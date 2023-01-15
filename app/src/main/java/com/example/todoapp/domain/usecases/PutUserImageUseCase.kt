package com.example.todoapp.domain.usecases

import com.example.todoapp.domain.repository.UserRepository
import okhttp3.MultipartBody

class PutUserImageUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: PutUserImageUseCase.Param): Unit?{
        return userRepository.putUserImage(param)
    }

    data class Param(
        val token: String,
        val uploadedFile: MultipartBody.Part
    )
}