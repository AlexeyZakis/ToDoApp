package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.db.addedItems.AddedItemDao
import com.example.todoapp.data.db.addedItems.AddedItemDb
import com.example.todoapp.data.db.deletedItems.DeletedItemDao
import com.example.todoapp.data.db.deletedItems.DeletedItemDb
import com.example.todoapp.data.network.NetworkConstants
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.di.LocalStorageQualifier
import com.example.todoapp.di.NetworkStorageQualifier
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Responsible for querying and manipulating data from the underlying data source
 **/
class TodoItemsRepositoryImpl(
    @NetworkStorageQualifier private val networkStorage: TaskStorage,
    @LocalStorageQualifier private val localStorage: TaskStorage,
    private val addedItemDao: AddedItemDao,
    private val deletedItemDao: DeletedItemDao,
) : TodoItemsRepository {
    private val _todoItems: MutableStateFlow<Map<String, TodoItem>> = MutableStateFlow(emptyMap())
    override val todoItems = _todoItems.asStateFlow()

    private val _hasInternet = MutableStateFlow(false)
    override val hasInternet = _hasInternet

    private val _hideDoneTask = MutableStateFlow(Constants.HIDE_DONE_TASK_DEFAULT)
    override val hideDoneTask = _hideDoneTask.asStateFlow()

    private val _isDataLoadedSuccessfully = MutableStateFlow(false)
    override val isDataLoadedSuccessfully = _isDataLoadedSuccessfully.asStateFlow()

    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        loadTodoItems()
    }

    private fun loadTodoItems() {
        repositoryScope.launch {
            if (!_hasInternet.value) {
                val localStorageResponse = localStorage.getList()
                localStorageResponse.data?.also { items ->
                    _todoItems.value = items
                    _isDataLoadedSuccessfully.value = true
                } ?: {
                    _isDataLoadedSuccessfully.value = false
                }
            }
        }
    }

    override fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        _hideDoneTask.update {
            hideDoneTask
        }
    }

    override val doneTaskCounter: Int
        get() = _todoItems.value.values.count { it.isDone }

    override suspend fun getTodoItem(todoItemId: String) =
        withContext(Dispatchers.IO) {
            if (_hasInternet.value) {
                val networkStorageResponse = networkStorage.getItem(todoItemId)
                if (networkStorageResponse.status.isSuccess()) {
                    StorageResult(
                        status = networkStorageResponse.status,
                        data = _todoItems.value[todoItemId]
                    )
                }
                networkStorageResponse
            } else {
                localStorage.getItem(todoItemId)
            }
        }

    override suspend fun addTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val localStorageResponse = localStorage.addItem(todoItem)
            addedItemDao.insertItem(
                AddedItemDb(
                    todoItem.id, todoItem.modificationDate
                )
            )
            addOrEditItem(todoItem)
            if (_hasInternet.value) {
                val networkStorageResponse = networkStorage.addItem(todoItem)
                networkStorageResponse
            } else {
                localStorageResponse
            }
        }

    override suspend fun deleteTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val localStorageResponse = localStorage.deleteItem(todoItem)
            deletedItemDao.insertItem(
                DeletedItemDb(
                    todoItem.id, todoItem.modificationDate
                )
            )
            _todoItems.update {
                val updatedValues = todoItems.value.toMutableMap()
                updatedValues.remove(todoItem.id)
                updatedValues.toMap()
            }
            if (_hasInternet.value) {
                val networkStorageResponse = networkStorage.deleteItem(todoItem)
                networkStorageResponse
            } else {
                localStorageResponse
            }
        }

    override suspend fun editTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            val localStorageResponse = localStorage.updateItem(todoItem)
            addOrEditItem(todoItem)
            if (_hasInternet.value) {
                val networkStorageResponse = networkStorage.updateItem(todoItem)
                networkStorageResponse
            } else {
                localStorageResponse
            }
        }

    private fun addOrEditItem(todoItem: TodoItem) {
        _todoItems.update {
            val updatedValues = todoItems.value.toMutableMap()
            updatedValues[todoItem.id] = todoItem
            updatedValues.toMap()
        }
    }

    override fun setConnectedStatus(isConnected: Boolean) {
        _hasInternet.value = isConnected
    }

    override fun destroy() {
        repositoryScope.cancel()
    }

    override suspend fun sync() =
        withContext(Dispatchers.IO) {
            Log.w(NetworkConstants.DEBUG, "Synchronization...")
            if (!_hasInternet.value) {
                _isDataLoadedSuccessfully.value = false
                return@withContext StorageResult(status = StorageResultStatus.ERROR, data = null)
            }
            val networkResponse = networkStorage.getList()
            val localResponse = localStorage.getList()

            if (!networkResponse.status.isSuccess() ||
                networkResponse.data == null ||
                localResponse.data == null
            ) {
                _isDataLoadedSuccessfully.value = false
                return@withContext StorageResult(status = networkResponse.status, data = null)
            }
            val serverData = networkResponse.data
            val localData = localResponse.data

            val localAddedItems = addedItemDao.getAll().associateBy { it.id }
            val localDeletedItems = deletedItemDao.getAll().associateBy { it.id }

            val serverDataSet = serverData.values.toSet()
            val localDataSet = localData.values.toSet()

            val localExtraData =
                localDataSet.minusWithComparator(serverDataSet, TodoItem.idComparator)
            val serverExtraData =
                serverDataSet.minusWithComparator(localDataSet, TodoItem.idComparator)
            val mutualData =
                localDataSet.intersectWithComparator(serverDataSet, TodoItem.idComparator)

            Log.d(RepositoryConstants.DEBUG, "serverData\n$serverData")
            Log.d(RepositoryConstants.DEBUG, "localData\n$localData")
            Log.v(RepositoryConstants.DEBUG, "_____________________")
            Log.d(RepositoryConstants.DEBUG, "localExtraData\n$localExtraData")
            Log.d(RepositoryConstants.DEBUG, "serverExtraData\n$serverExtraData")
            Log.d(RepositoryConstants.DEBUG, "mutualData\n$mutualData")
            Log.v(RepositoryConstants.DEBUG, "_____________________")

            if (SyncHandler.localExtraDataHandle(
                    networkStorage,
                    localStorage,
                    localExtraData,
                    localAddedItems
                ) ||
                SyncHandler.serverExtraDataHandle(
                    networkStorage,
                    localStorage,
                    serverExtraData,
                    localDeletedItems
                ) ||
                SyncHandler.mutualDataHandle(
                    networkStorage,
                    localStorage,
                    mutualData,
                    serverData,
                    localData
                )
            ) {
                Log.e(RepositoryConstants.DEBUG, "Sync error")

                _isDataLoadedSuccessfully.value = false
                return@withContext StorageResult(status = StorageResultStatus.ERROR, data = null)
            } else {
                addedItemDao.deleteAll()
                deletedItemDao.deleteAll()

                val request = localStorage.getList()

                _todoItems.value = request.data
                    ?: return@withContext StorageResult(status = request.status, data = null)

                Log.i(RepositoryConstants.DEBUG, "Sync success")

                _isDataLoadedSuccessfully.value = true
                return@withContext StorageResult(status = StorageResultStatus.SUCCESS, data = null)
            }
        }
}
