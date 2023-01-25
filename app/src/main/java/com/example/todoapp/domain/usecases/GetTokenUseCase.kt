package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository

class GetTokenUseCase(private val userRepository: UserRepository) {

    fun execute(): String? {
        return userRepository.getToken()
    }
}