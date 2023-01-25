package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.models.UserInfoModel

class GetUserInfoUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: String) : UserInfoModel {
        return userRepository.getUserInfo(param)
    }
}