package com.example.todoapp.domain.usecases

import android.graphics.Bitmap
import com.example.todoapp.domain.repository.UserRepository
import okhttp3.ResponseBody

class GetUserImageUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: GetUserImageUseCase.Param): Bitmap? {
        return userRepository.getUserImage(param)
    }

    data class Param(
        val token: String,
        val imageId: String
    )
}