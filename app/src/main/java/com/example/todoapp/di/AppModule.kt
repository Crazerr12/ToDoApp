package com.example.todoapp.di

import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.presentation.category.CategoryFragmentViewModel
import com.example.todoapp.presentation.login.LoginFragmentViewModel
import com.example.todoapp.presentation.profile.ProfileFragmentViewModel
import com.example.todoapp.presentation.register.RegisterFragmentViewModel
import com.example.todoapp.presentation.started.GetStartedFragmentViewModel
import com.example.todoapp.presentation.tasks.AddTaskFragmentViewModel
import com.example.todoapp.presentation.tasks.TasksFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<ProfileFragmentViewModel> {
        ProfileFragmentViewModel(
            getUserInfoUseCase = get(),
            getTokenUseCase = get(),
            getUserImageUseCase = get(),
            getRoundedBitmapUseCase = get(),
            exitFromAccountUseCase = get(),
            putUserImageUseCase = get()
        )
    }

    viewModel<CategoryFragmentViewModel> {
        CategoryFragmentViewModel(
            getTokenUseCase = get(),
            getTasksUseCase = get(),
            deleteTaskUseCase = get(),
            putCheckBoxUseCase = get()
        )
    }

    viewModel<LoginFragmentViewModel> {
        LoginFragmentViewModel(loginByEmailUseCase = get(), saveTokenUseCase = get())
    }

    viewModel<RegisterFragmentViewModel> {
        RegisterFragmentViewModel(registerByEmailUseCase = get(), saveTokenUseCase = get())
    }

    viewModel<GetStartedFragmentViewModel> {
        GetStartedFragmentViewModel(getTokenUseCase = get())
    }

    viewModel<AddTaskFragmentViewModel> {
        AddTaskFragmentViewModel(getTokenUseCase = get(), addTaskUseCase = get())
    }

    viewModel<TasksFragmentViewModel> {
        TasksFragmentViewModel(getTokenUseCase = get(), getTasksUseCase = get())
    }
}