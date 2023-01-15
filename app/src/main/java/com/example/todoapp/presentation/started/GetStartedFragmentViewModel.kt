package com.example.todoapp.presentation.started

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.GetTokenUseCase
import kotlinx.coroutines.launch

class GetStartedFragmentViewModel(private val getTokenUseCase: GetTokenUseCase) : ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun getToken() {
            _token.value = getTokenUseCase.execute()

    }
}