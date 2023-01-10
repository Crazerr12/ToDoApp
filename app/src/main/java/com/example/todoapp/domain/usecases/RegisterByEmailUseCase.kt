package com.example.todoapp.domain.usecases

import androidx.navigation.NavController
import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.RegistrationModel

class RegisterByEmailUseCase(private val userRepository: UserRepository) {

    fun execute(
        userRegistration: RegistrationModel,
        navigation: NavController
    ): Unit {
        return userRepository.saveRegister(userRegistration, navigation)
    }
}