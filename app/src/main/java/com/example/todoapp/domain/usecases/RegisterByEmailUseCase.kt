package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.TokenModel

class RegisterByEmailUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: RegisterByEmailUseCase.Param): TokenModel? {
        return userRepository.userRegister(param)
    }

    data class Param(
        val name: String,
        val email: String,
        val password: String
    )
}