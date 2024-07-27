package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.EmptyStorage
import com.example.todoapp.data.db.addedItems.AddedItemDao
import com.example.todoapp.data.db.addedItems.AddedItemsDatabase
import com.example.todoapp.data.db.deletedItems.DeletedItemDao
import com.example.todoapp.data.db.deletedItems.DeletedItemsDatabase
import com.example.todoapp.data.db.todoItems.TodoItemsDatabase
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.data.storage.RuntimeStorage
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
class TestStorageModule {
    @Provides
    @Singleton
    @NetworkStorageQualifier
    fun provideNetworkStorage(): TaskStorage =
        EmptyStorage()

    @Provides
    @Singleton
    @LocalStorageQualifier
    fun provideLocalStorage(): TaskStorage =
        RuntimeStorage()
}