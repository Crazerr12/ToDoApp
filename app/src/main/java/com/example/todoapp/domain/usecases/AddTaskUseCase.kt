package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.TaskModelPost

class AddTaskUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: AddTaskUseCase.Param): Unit? {
        return userRepository.addTask(param)
    }

    data class Param(
        val token: String,
        val taskInfo: TaskModelPost
    )
}