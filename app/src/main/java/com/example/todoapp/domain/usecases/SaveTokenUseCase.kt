package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository

class SaveTokenUseCase(private val userRepository: UserRepository) {

    fun execute(param: SaveTokenUseCase.Param) {
        userRepository.saveToken(param)
    }

    data class Param(
        val token: String
    )
}