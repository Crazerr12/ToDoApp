package com.example.todoapp.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.data.storage.UserStorage
import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.LoginModel
import com.example.todoapp.presentation.models.RegistrationModel
import com.example.todoapp.presentation.models.TokenModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImplementation(private val userStorage: UserStorage) : UserRepository {

    override fun saveLogin(
        userLogin: LoginModel,
        navigation: NavController
    ) {
        return RetrofitInstance.retrofit.login(userLogin).enqueue(object : Callback<TokenModel> {
            override fun onResponse(
                call: Call<TokenModel>,
                response: Response<TokenModel>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: "No token"
                    userStorage.saveToken(token)
                    navigation.navigate(R.id.action_loginFragment_to_switchFragment)
                }
            }

            override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    override fun saveRegister(
        userRegistration: RegistrationModel,
        navigation: NavController
    ): Unit {
        return RetrofitInstance.retrofit.registration(userRegistration)
            .enqueue(object : Callback<TokenModel> {
                override fun onResponse(
                    call: Call<TokenModel>,
                    response: Response<TokenModel>
                ) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token ?: "No token"
                        userStorage.saveToken(token)
                        navigation.navigate(R.id.action_registerFragment_to_switchFragment)
                    }
                }

                override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                    Log.e(MotionEffect.TAG, "onFailure: ${t.message}")
                }

            })
    }

}