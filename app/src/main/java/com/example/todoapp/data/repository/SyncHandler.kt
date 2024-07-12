package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.db.addedItems.AddedItemDb
import com.example.todoapp.data.db.deletedItems.DeletedItemDb
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.di.LocalStorageQualifier
import com.example.todoapp.di.NetworkStorageQualifier
import com.example.todoapp.domain.models.TodoItem

object SyncHandler {
    suspend fun localExtraDataHandle(
        @NetworkStorageQualifier networkStorage: TaskStorage,
        @LocalStorageQualifier localStorage: TaskStorage,
        localExtraData: Set<TodoItem>,
        localAddedItems: Map<String, AddedItemDb>
    ): Boolean {
        localExtraData.forEach { localItem ->
            Log.v(
                RepositoryConstants.DEBUG,
                "localExtraDataHandle\n$localItem"
            )
            localAddedItems[localItem.id]?.also {
                networkStorage.addItem(localItem).also {
                    if (!it.status.isSuccess()) {
                        Log.e(
                            RepositoryConstants.DEBUG,
                            "localExtraDataHandle networkStorage addItem error"
                        )
                        return true
                    }
                    else {
                        Log.i(
                            RepositoryConstants.DEBUG,
                            "localExtraDataHandle networkStorage addItem success"
                        )
                    }
                }
            } ?: localStorage.deleteItem(localItem).also {
                if (!it.status.isSuccess()) {
                    Log.e(
                        RepositoryConstants.DEBUG,
                        "localExtraDataHandle localStorage deleteItem error"
                    )
                    return true
                }
                else {
                    Log.i(
                        RepositoryConstants.DEBUG,
                        "localExtraDataHandle localStorage deleteItem success"
                    )
                }
            }
        }
        return false
    }

    suspend fun serverExtraDataHandle(
        @NetworkStorageQualifier networkStorage: TaskStorage,
        @LocalStorageQualifier localStorage: TaskStorage,
        serverExtraData: Set<TodoItem>,
        localDeletedItems: Map<String, DeletedItemDb>
    ): Boolean {
        serverExtraData.forEach { serverItem ->
            Log.v(
                RepositoryConstants.DEBUG,
                "serverExtraDataHandle\n$serverItem"
            )
            localDeletedItems[serverItem.id]?.also { localItem ->
                if (localItem.date > serverItem.modificationDate) {
                    Log.d(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle localItem.editDate > serverItem.editDate"
                    )
                    Log.d(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle networkStorage deleteItem"
                    )
                    networkStorage.deleteItem(serverItem)
                } else {
                    Log.d(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle localItem.editDate <= serverItem.editDate"
                    )
                    Log.d(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle localStorage addItem"
                    )
                    localStorage.addItem(serverItem)
                }.also {
                    if (!it.status.isSuccess()) {
                        Log.e(
                            RepositoryConstants.DEBUG,
                            "serverExtraDataHandle success"
                        )
                        return true
                    }
                    else {
                        Log.i(
                            RepositoryConstants.DEBUG,
                            "serverExtraDataHandle error"
                        )
                    }
                }
            } ?: localStorage.addItem(serverItem).also {
                if (!it.status.isSuccess()) {
                    Log.e(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle localStorage addItem error"
                    )
                    return true
                }
                else {
                    Log.i(
                        RepositoryConstants.DEBUG,
                        "serverExtraDataHandle localStorage addItem success"
                    )
                }
            }
        }
        return false
    }

    suspend fun mutualDataHandle(
        @NetworkStorageQualifier networkStorage: TaskStorage,
        @LocalStorageQualifier localStorage: TaskStorage,
        mutualData: Set<TodoItem>,
        serverData: Map<String, TodoItem>,
        localData: Map<String, TodoItem>
    ): Boolean {
        mutualData.forEach { item ->
            Log.v(
                RepositoryConstants.DEBUG,
                "mutualDataHandle\n$item"
            )
            if (serverData[item.id] == localData[item.id]) {
                Log.d(
                    RepositoryConstants.DEBUG,
                    "mutualDataHandle same items"
                )
                return@forEach
            }
            if ((serverData[item.id]?.modificationDate ?: 0) >=
                (localData[item.id]?.modificationDate ?: 0)
            ) {
                Log.d(
                    RepositoryConstants.DEBUG,
                    "mutualDataHandle serverData.editDate >= serverItem.localData"
                )
                localStorage.updateItem(serverData[item.id] ?: TodoItem()).also {
                    if (!it.status.isSuccess()) {
                        Log.e(
                            RepositoryConstants.DEBUG,
                            "mutualDataHandle localStorage updateItem error"
                        )
                        return true
                    }
                    else {
                        Log.i(
                            RepositoryConstants.DEBUG,
                            "mutualDataHandle localStorage updateItem success"
                        )
                    }
                }
            } else {
                Log.d(
                    RepositoryConstants.DEBUG,
                    "mutualDataHandle serverData.editDate < serverItem.localData"
                )
                networkStorage.updateItem(localData[item.id] ?: TodoItem()).also {
                    if (!it.status.isSuccess()) {
                        Log.e(
                            RepositoryConstants.DEBUG,
                            "mutualDataHandle networkStorage updateItem error"
                        )
                        return true
                    }
                    else {
                        Log.i(
                            RepositoryConstants.DEBUG,
                            "mutualDataHandle networkStorage updateItem success"
                        )
                    }
                }
            }
        }
        return false
    }
}