package com.example.todoapp.di

import com.example.todoapp.data.db.todoItems.TodoItemDao
import com.example.todoapp.data.network.NetworkInit
import com.example.todoapp.data.storage.DbStorage
import com.example.todoapp.data.storage.NetworkStorage
import com.example.todoapp.data.storage.TaskStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    @NetworkStorageQualifier
    fun provideNetworkStorage(): TaskStorage =
        NetworkStorage(NetworkInit.networkApi)

    @Provides
    @Singleton
    @LocalStorageQualifier
    fun provideLocalStorage(todoItemDao: TodoItemDao): TaskStorage =
        DbStorage(todoItemDao)
}