package com.example.todoapp

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.presentation.screens.navigation.AppNavigation
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.receivers.ConnectivityReceiver
import com.example.todoapp.workers.UpdateDataWorker
import com.yandex.authsdk.YandexAuthException
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var todoItemsRepository: TodoItemsRepository

    private lateinit var connectivityReceiver: ConnectivityReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityReceiver = ConnectivityReceiver(todoItemsRepository)
        scheduleDataUpdate(applicationContext)

        val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
        val launcher = registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions()
        launcher.launch(loginOptions)
    }
    private fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> onSuccessAuth(result.token)
            is YandexAuthResult.Failure -> onProccessError(result.exception)
            YandexAuthResult.Cancelled -> onCancelled()
        }
    }

    private fun onCancelled() {
        Log.d(Constants.YANDEX_AUTH_DEBUG, "Cancelled")
    }

    private fun onProccessError(exception: YandexAuthException) {
        Log.e(Constants.YANDEX_AUTH_DEBUG, "Error", exception)
    }

    private fun onSuccessAuth(token: YandexAuthToken) {
        Log.d(Constants.YANDEX_AUTH_DEBUG, "Success: $token")
        setContent {
            AppTheme(MainTheme) {
                AppNavigation()
            }
        }
    }

    private fun scheduleDataUpdate(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateDataWorkRequest = PeriodicWorkRequestBuilder<UpdateDataWorker>(
            Constants.UPDATE_WORKER_BETWEEN_UPDATES_HOURS,
            TimeUnit.HOURS
        )
            .setInitialDelay(
                duration = 15,
                TimeUnit.SECONDS
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                backoffDelay = Constants.UPDATE_WORKER_BETWEEN_RETRY_HOURS,
                TimeUnit.HOURS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.UPDATE_DATA_WORK,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            updateDataWorkRequest
        )
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectivityReceiver)
    }
}
