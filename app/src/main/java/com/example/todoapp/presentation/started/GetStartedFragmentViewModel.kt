package com.example.todoapp.presentation.started

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.domain.usecases.GetTokenUseCase

class GetStartedFragmentViewModel(getTokenUseCase: GetTokenUseCase) : ViewModel() {

    val token = MutableLiveData<String?>(getTokenUseCase.execute())
}