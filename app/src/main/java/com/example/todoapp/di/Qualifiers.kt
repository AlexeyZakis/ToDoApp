package com.example.todoapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkStorageQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalStorageQualifier
