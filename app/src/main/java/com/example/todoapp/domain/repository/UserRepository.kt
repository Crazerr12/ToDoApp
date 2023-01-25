package com.example.todoapp.domain.repository

import android.graphics.Bitmap
import com.example.todoapp.domain.usecases.*
import com.example.todoapp.presentation.models.TaskModelGet
import com.example.todoapp.presentation.models.TokenModel
import com.example.todoapp.presentation.models.UserInfoModel

interface UserRepository {

    suspend fun userLogin(
        param: LoginByEmailUseCase.Param
    ): TokenModel?

    suspend fun userRegister(
        param: RegisterByEmailUseCase.Param
    ): TokenModel?

    fun getToken(): String?

    fun saveToken(param: SaveTokenUseCase.Param)

    suspend fun getUserInfo(param: String) : UserInfoModel

    suspend fun getUserImage(param: GetUserImageUseCase.Param): Bitmap?

    suspend fun getTasks(param: String): List<TaskModelGet>

    suspend fun addTask(param: AddTaskUseCase.Param): Unit?

    suspend fun deleteTask(param: DeleteTaskUseCase.Param): Unit?

    suspend fun putCheckBox(param: DeleteTaskUseCase.Param): Unit?

    suspend fun putUserImage(param: PutUserImageUseCase.Param): Unit?

    fun getRoundedBitmap(param: Bitmap): Bitmap?

    fun exitFromAccount()
}