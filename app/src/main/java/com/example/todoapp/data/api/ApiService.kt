package com.example.todoapp.data.api

import com.example.todoapp.domain.usecases.AddTaskUseCase
import com.example.todoapp.domain.usecases.RegisterByEmailUseCase
import com.example.todoapp.domain.usecases.LoginByEmailUseCase
import com.example.todoapp.presentation.models.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST("api/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body body: LoginByEmailUseCase.Param): Response<TokenModel>

    @POST("api/auth/registration")
    @Headers("Content-Type: application/json")
    suspend fun registration(@Body body: RegisterByEmailUseCase.Param): Response<TokenModel>

    @GET("api/user")
    suspend fun getUserInfo(
        @Header("Authorization") token: String?
    ): Response<UserInfoModel>

    @GET("api/todos")
    suspend fun getTodos(
        @Header("Authorization") token: String?
    ): Response<List<TaskModelGet>>

    @POST("api/todos")
    suspend fun addTask(
        @Header("Authorization") token: String?,
        @Body body: TaskModelPost
    ): Response<Unit>

    @DELETE("api/todos/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String?,
        @Path("id") id: String
    ): Response<Unit>

    @PUT("api/todos/mark/{id}")
    suspend fun putCheckbox(
        @Header("Authorization") token: String?,
        @Path("id") id: String
    ): Response<Unit>

    @Multipart
    @POST("api/user/photo")
    suspend fun postImage(
        @Header("Authorization") token: String?,
        @Part uploadedFile: MultipartBody.Part
    ): Response<Unit>

    @GET("api/user/photo/{fileId}")
    suspend fun getImage(
        @Header("Authorization") token: String?,
        @Path ("fileId") fileId: String
    ): Response<ResponseBody>
}