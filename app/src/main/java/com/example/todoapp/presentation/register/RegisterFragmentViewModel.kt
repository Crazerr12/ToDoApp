package com.example.todoapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.RegisterByEmailUseCase
import com.example.todoapp.domain.usecases.SaveTokenUseCase
import kotlinx.coroutines.launch

class RegisterFragmentViewModel(
    private val registerByEmailUseCase: RegisterByEmailUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
): ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun userRegister(param: RegisterByEmailUseCase.Param){
        viewModelScope.launch {
            _token.value = registerByEmailUseCase.execute(param)?.token
            _token.value?.let { saveTokenUseCase.execute(SaveTokenUseCase.Param(it)) }
        }
    }
}