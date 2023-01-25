package com.example.todoapp.di

import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.data.storage.UserStorage
import com.example.todoapp.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }
}