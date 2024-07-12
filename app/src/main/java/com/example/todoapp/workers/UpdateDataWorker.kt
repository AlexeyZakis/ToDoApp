package com.example.todoapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Update data in background
 **/
@HiltWorker
class UpdateDataWorker @AssistedInject constructor(
    @Assisted private val todoItemsRepository: TodoItemsRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        Log.i(Constants.UPDATE_DATA_WORK_DEBUG, "Update data background work: Running")
        return try {
            val result = todoItemsRepository.sync()
            if (result.status.isSuccess()) {
                Log.d(Constants.UPDATE_DATA_WORK_DEBUG, "Update data background work: Success")
                Result.success()
            } else {
                Log.w(Constants.UPDATE_DATA_WORK_DEBUG, "Update data background work: Retrying")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(Constants.UPDATE_DATA_WORK_DEBUG, "Update data background work: Exception", e)
            Result.failure()
        }
    }
}
