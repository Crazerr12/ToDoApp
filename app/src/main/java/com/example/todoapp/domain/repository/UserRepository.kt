package com.example.todoapp.domain.repository

import androidx.navigation.NavController
import com.example.todoapp.presentation.models.LoginModel
import com.example.todoapp.presentation.models.RegistrationModel

interface UserRepository {

    fun saveLogin(
        userLogin: LoginModel,
        navigation: NavController
    ): Unit

    fun saveRegister(
        userRegistration: RegistrationModel,
        navigation: NavController
    ): Unit
}