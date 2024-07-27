package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.db.addedItems.AddedItemsDatabase
import com.example.todoapp.data.db.deletedItems.DeletedItemsDatabase
import com.example.todoapp.data.db.todoItems.TodoItemsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
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