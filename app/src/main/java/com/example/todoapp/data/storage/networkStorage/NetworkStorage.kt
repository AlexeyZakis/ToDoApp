package com.example.todoapp.data.storage.networkStorage

import com.example.todoapp.data.network.Network
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.testStorage.TestStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem

class NetworkStorage: TaskStorage {
    override suspend fun get(): Map<String, TodoItem> {
        Network.getItemsList()
//        Network.deleteAll()
//        Network.getItemsList()
        val elem = TestStorage().get().values.toList().first()
//
        Network.addItem(elem)
        Network.getItem(elem.id)
//        Network.updateList(Items(TestStorage().get().values.toList()))
//        Network.getItemsList()
//        Network.updateItem(elem.copy(
//            taskText = "NewItem"
//        ))
//        Network.getItemsList()
//        Network.deleteItem(elem.id)
        return Network.getItemsList()
    }
}
