package com.example.todoapp.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.todoapp.domain.usecases.SaveTokenUseCase

private const val SHARED_PREFS_NAME = "SHARED_PREF"

class SharedPrefUserStorage(context: Context) : UserStorage {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveToken(param: SaveTokenUseCase.Param) {
        sharedPreferences.edit().putString("TOKEN", param.token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("TOKEN", "")
    }

    override fun exitFromAccount() {
        sharedPreferences.edit().clear().apply()
    }


}