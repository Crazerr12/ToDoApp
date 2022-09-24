package com.example.todoapp.presentation.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit: ApiService = Retrofit.Builder()
        .baseUrl("http://45.144.64.179/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}