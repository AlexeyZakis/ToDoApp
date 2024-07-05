package com.example.todoapp

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import com.example.todoapp.presentation.screens.navigation.AppNavigation
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.workers.UpdateDataWorker
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.receivers.ConnectivityReceiver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectivityReceiver: ConnectivityReceiver
    @Inject
    lateinit var todoItemsRepository: TodoItemsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleDataUpdate(applicationContext)
        connectivityReceiver = ConnectivityReceiver(todoItemsRepository)

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