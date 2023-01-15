package com.example.todoapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.SaveTokenUseCase
import com.example.todoapp.domain.usecases.LoginByEmailUseCase
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val loginByEmailUseCase: LoginByEmailUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
): ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun userLogin(param: LoginByEmailUseCase.Param){
        viewModelScope.launch {
            _token.value = loginByEmailUseCase.execute(param)?.token
            _token.value?.let { saveTokenUseCase.execute(SaveTokenUseCase.Param(it)) }
        }
    }
}