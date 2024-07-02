package com.example.todoapp.di

import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.domain.usecase.AddTodoItemUseCase
import com.example.todoapp.domain.usecase.ChangeDoneTaskVisibilityUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetIsDoneTaskHiddenUseCase
import com.example.todoapp.domain.usecase.GetItemListUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.domain.usecase.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideAddTodoItemUseCase(todoItemsRepository: TodoItemsRepository) =
        AddTodoItemUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideChangeDoneTaskVisibilityUseCase(todoItemsRepository: TodoItemsRepository) =
        ChangeDoneTaskVisibilityUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideDeleteTodoItemUseCase(todoItemsRepository: TodoItemsRepository) =
        DeleteTodoItemUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideEditTodoItemUseCase(todoItemsRepository: TodoItemsRepository) =
        EditTodoItemUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideGetItemListUseCase(todoItemsRepository: TodoItemsRepository) =
        GetItemListUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideGetNumberOfDoneTaskUseCase(todoItemsRepository: TodoItemsRepository) =
        GetNumberOfDoneTaskUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideGetTodoItemUseCase(todoItemsRepository: TodoItemsRepository) =
        GetTodoItemUseCase(todoItemsRepository = todoItemsRepository)
    @Provides
    fun provideGetIsDoneTaskHiddenUseCase(todoItemsRepository: TodoItemsRepository) =
        GetIsDoneTaskHiddenUseCase(todoItemsRepository = todoItemsRepository)
}