package com.example.todoapp.domain.usecases

import android.graphics.Bitmap
import com.example.todoapp.domain.repository.UserRepository
import okhttp3.ResponseBody

class GetRoundedBitmapUseCase(
    private val userRepository: UserRepository
) {

    fun execute(param: Bitmap): Bitmap?{
        return userRepository.getRoundedBitmap(param)
    }
}