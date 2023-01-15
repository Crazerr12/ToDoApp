package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository

class DeleteTaskUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: DeleteTaskUseCase.Param): Unit?{
        return userRepository.deleteTask(param)
    }

    data class Param(
        val token: String,
        val taskId: String
    )
}