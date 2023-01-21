package com.example.todoapp.di

import com.example.todoapp.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {

    factory<AddTaskUseCase> {
        AddTaskUseCase(userRepository = get())
    }

    factory<DeleteTaskUseCase> {
        DeleteTaskUseCase(userRepository = get())
    }

    factory<ExitFromAccountUseCase> {
        ExitFromAccountUseCase(userRepository = get())
    }

    factory<GetRoundedBitmapUseCase> {
        GetRoundedBitmapUseCase(userRepository = get())
    }

    factory<GetTasksUseCase> {
        GetTasksUseCase(userRepository = get())
    }

    factory<GetTokenUseCase> {
        GetTokenUseCase(userRepository = get())
    }

    factory<GetUserImageUseCase> {
        GetUserImageUseCase(userRepository = get())
    }

    factory<GetUserInfoUseCase> {
        GetUserInfoUseCase(userRepository = get())
    }

    factory<LoginByEmailUseCase> {
        LoginByEmailUseCase(userRepository = get())
    }

    factory<PutCheckBoxUseCase> {
        PutCheckBoxUseCase(userRepository = get())
    }

    factory<PutUserImageUseCase> {
        PutUserImageUseCase(userRepository = get())
    }

    factory<RegisterByEmailUseCase> {
        RegisterByEmailUseCase(userRepository = get())
    }

    factory<SaveTokenUseCase> {
        SaveTokenUseCase(userRepository = get())
    }
}