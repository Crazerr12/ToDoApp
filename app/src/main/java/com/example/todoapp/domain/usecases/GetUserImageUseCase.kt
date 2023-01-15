package com.example.todoapp.domain.usecases

import androidx.lifecycle.ViewModel
import com.example.todoapp.domain.repository.UserRepository
import okhttp3.ResponseBody

class GetUserImageUseCase(private val userRepository: UserRepository): ViewModel() {

    suspend fun execute(param: GetUserImageUseCase.Param): ResponseBody? {
        return userRepository.getUserImage(param)
    }

    data class Param(
        val token: String,
        val imageId: String
    )
}