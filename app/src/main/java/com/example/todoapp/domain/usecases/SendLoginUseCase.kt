package com.example.todoapp.domain.usecases


import androidx.navigation.NavController
import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.LoginModel

class SendLoginUseCase(private val userRepository: UserRepository) {

    fun execute(userLogin: LoginModel, navigation: NavController) {
        return userRepository.saveLogin(userLogin, navigation)
    }
}