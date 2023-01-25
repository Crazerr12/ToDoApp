package com.example.todoapp.domain.usecases


import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.TokenModel

class LoginByEmailUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: LoginByEmailUseCase.Param): TokenModel? {
        return userRepository.userLogin(param)
    }
    data class Param (
        val email : String,
        val password : String
    )
}