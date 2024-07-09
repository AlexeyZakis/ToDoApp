package com.example.todoapp.di

import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.domain.usecase.AddTodoItemUseCase
import com.example.todoapp.domain.usecase.ChangeDoneTaskVisibilityUseCase
import com.example.todoapp.domain.usecase.CheckHasInternetUseCase
import com.example.todoapp.domain.usecase.CheckIsDoneTaskHiddenUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.DestroyRepositoryUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetIsDataLoadedSuccessfullyUseCase
import com.example.todoapp.domain.usecase.GetItemListUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.domain.usecase.GetTodoItemUseCase
import com.example.todoapp.domain.usecase.RefreshDataUseCase
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
    fun provideCheckIsDoneTaskHiddenUseCase(todoItemsRepository: TodoItemsRepository) =
        CheckIsDoneTaskHiddenUseCase(todoItemsRepository = todoItemsRepository)

    @Provides
    fun provideGetIsDataLoadedSuccessfullyUseCase(todoItemsRepository: TodoItemsRepository) =
        GetIsDataLoadedSuccessfullyUseCase(todoItemsRepository = todoItemsRepository)

    @Provides
    fun provideRefreshDataUseCase(todoItemsRepository: TodoItemsRepository) =
        RefreshDataUseCase(todoItemsRepository = todoItemsRepository)

    @Provides
    fun provideDestroyRepositoryUseCase(todoItemsRepository: TodoItemsRepository) =
        DestroyRepositoryUseCase(todoItemsRepository = todoItemsRepository)

    @Provides
    fun provideCheckHasInternetUseCase(todoItemsRepository: TodoItemsRepository) =
        CheckHasInternetUseCase(todoItemsRepository = todoItemsRepository)
}
