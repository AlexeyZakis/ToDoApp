package com.example.todoapp.di

import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.localStorage.LocalStorage
import com.example.todoapp.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideTaskStorage(): TaskStorage {
        return LocalStorage()
    }
    @Provides
    @Singleton
    fun provideTodoItemsRepository(taskStorage: TaskStorage): TodoItemsRepository {
        return TodoItemsRepositoryImpl(taskStorage = taskStorage)
    }
}