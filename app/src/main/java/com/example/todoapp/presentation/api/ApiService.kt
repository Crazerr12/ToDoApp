package com.example.todoapp.presentation.api

import com.example.todoapp.presentation.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    @Headers("Content-Type: application/json")
    fun login(@Body body: LoginModel): Call<TokenModel>

    @POST("api/auth/registration")
    @Headers("Content-Type: application/json")
    fun registration(@Body body: RegistrationModel): Call<TokenModel>

    @GET("api/user")
    fun getInfo(
        @Header("Authorization") token: String?
    ): Call<RegistrationModel>

    @GET("api/todos")
    fun getTodos(
        @Header("Authorization") token: String?
    ): Call<List<TaskModelGet>>

    @POST("api/todos")
    fun addTask(
        @Header("Authorization") token: String?,
        @Body body: TaskModelPost
    ): Call<Unit>

    @DELETE("api/todos/{id}")
    fun deleteTask(
        @Header("Authorization") token: String?,
        @Path ("id") id : String
    ): Call<Unit>
}