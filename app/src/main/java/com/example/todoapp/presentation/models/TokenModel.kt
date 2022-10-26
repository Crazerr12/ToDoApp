package com.example.todoapp.presentation.models

import com.google.gson.annotations.SerializedName

data class TokenModel (
    @SerializedName("access_token")
    val token : String
)