package com.example.todoapp.data.storage

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFS_NAME = "SHARED_PREF"

class SharedPrefUserStorage(context: Context) : UserStorage {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("TOKEN", token).apply()
    }
}