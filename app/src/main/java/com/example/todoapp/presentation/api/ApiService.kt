package com.example.todoapp.presentation.api

import com.example.todoapp.presentation.models.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
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
    ): Call<UserInfoModel>

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
        @Path("id") id: String
    ): Call<Unit>

    @PUT("api/todos/mark/{id}")
    fun putCheckbox(
        @Header("Authorization") token: String?,
        @Path("id") id: String
    ): Call<Unit>

    @Multipart
    @POST("api/user/photo")
    fun postImage(
        @Header("Authorization") token: String?,
        @Part uploadedFile: MultipartBody.Part
    ): Call<Unit>

    @GET("api/user/photo/{fileId}")
    fun getImage(
        @Header("Authorization") token: String?,
        @Path ("fileId") fileId: String
    ): Call<ResponseBody>
}