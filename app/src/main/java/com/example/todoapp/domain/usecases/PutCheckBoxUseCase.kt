package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository

class PutCheckBoxUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: DeleteTaskUseCase.Param): Unit?{
        return userRepository.putCheckBox(param)
    }
}