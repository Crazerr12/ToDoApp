package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository

class ExitFromAccountUseCase(private val userRepository: UserRepository) {

    fun execute(){
        userRepository.exitFromAccount()
    }
}