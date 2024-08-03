package com.example.todoapp.di

import com.example.todoapp.data.db.addedItems.AddedItemDao
import com.example.todoapp.data.db.deletedItems.DeletedItemDao
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        @NetworkStorageQualifier networkStorage: TaskStorage,
        @LocalStorageQualifier localStorage: TaskStorage,
        addedItemDao: AddedItemDao,
        deletedItemDao: DeletedItemDao,
    ): TodoItemsRepository =
        TodoItemsRepositoryImpl(
            networkStorage = networkStorage,
            localStorage = localStorage,
            addedItemDao = addedItemDao,
            deletedItemDao = deletedItemDao,
        )
}
