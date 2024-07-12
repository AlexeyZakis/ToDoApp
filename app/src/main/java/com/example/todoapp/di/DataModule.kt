package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.db.addedItems.AddedItemDao
import com.example.todoapp.data.db.addedItems.AddedItemsDatabase
import com.example.todoapp.data.db.deletedItems.DeletedItemDao
import com.example.todoapp.data.db.deletedItems.DeletedItemsDatabase
import com.example.todoapp.data.db.todoItems.TodoItemDao
import com.example.todoapp.data.db.todoItems.TodoItemsDatabase
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.data.storage.DbStorage
import com.example.todoapp.data.storage.NetworkStorage
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    @NetworkStorageQualifier
    fun provideNetworkStorage(): TaskStorage = NetworkStorage()

    @Provides
    @Singleton
    @LocalStorageQualifier
    fun provideLocalStorage(todoItemDao: TodoItemDao): TaskStorage = DbStorage(todoItemDao)

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

    @Provides
    @Singleton
    fun provideTodoItemsDatabase(@ApplicationContext appContext: Context) =
        TodoItemsDatabase.getDatabase(appContext)

    @Provides
    fun provideTodoItemDao(database: TodoItemsDatabase) =
        database.todoItemDao()

    @Provides
    @Singleton
    fun provideAddedItemsDatabase(@ApplicationContext appContext: Context) =
        AddedItemsDatabase.getDatabase(appContext)

    @Provides
    fun provideAddedItemDao(database: AddedItemsDatabase) =
        database.addedItemDao()

    @Provides
    @Singleton
    fun provideDeletedItemsDatabase(@ApplicationContext appContext: Context) =
        DeletedItemsDatabase.getDatabase(appContext)

    @Provides
    fun provideDeletedItemDao(database: DeletedItemsDatabase) =
        database.deletedItemDao()
}
