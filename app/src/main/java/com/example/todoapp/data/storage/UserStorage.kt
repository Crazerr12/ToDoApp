package com.example.todoapp.data.storage

import com.example.todoapp.domain.usecases.SaveTokenUseCase

interface UserStorage {

    fun saveToken(param: SaveTokenUseCase.Param)

    fun getToken(): String?

    fun exitFromAccount()
}