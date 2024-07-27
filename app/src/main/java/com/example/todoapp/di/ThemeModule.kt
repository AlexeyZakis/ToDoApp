package com.example.todoapp.di

import com.example.todoapp.presentation.data.ThemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ThemeModule {
    @Provides
    @Singleton
    fun provideThemeRepository() =
        ThemeRepository()
}