package com.example.todoapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants

class ConnectivityReceiver(
    private val todoItemsRepository: TodoItemsRepository
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(Constants.CONNECTIVITY_RECEIVER_DEBUG, "Launch")
        if (context != null) {
            if (isConnected(context)) {
                Log.d(Constants.CONNECTIVITY_RECEIVER_DEBUG, "Connected to Internet")
            }
            else {
                Log.d(Constants.CONNECTIVITY_RECEIVER_DEBUG, "Disconnected from Internet")
            }
            todoItemsRepository.setConnectedStatus(isConnected(context))
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}