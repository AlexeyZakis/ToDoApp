package com.example.todoapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkRequest
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
import com.example.todoapp.workers.UpdateDataWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var todoItemsRepository: TodoItemsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleDataUpdate(applicationContext)

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .addTransportType(TRANSPORT_WIFI)
            .addTransportType(TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d(Constants.CONNECTIVITY_DEBUG, "Connected to Internet")
                todoItemsRepository.setConnectedStatus(isConnected = true)
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d(Constants.CONNECTIVITY_DEBUG, "Disconnected from Internet")
                todoItemsRepository.setConnectedStatus(isConnected = false)
            }
        }

        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

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
}
