package com.example.todoapp.presentation.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.AddTaskUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.presentation.models.TaskModelPost
import kotlinx.coroutines.launch

class AddTaskFragmentViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val addTaskUseCase: AddTaskUseCase
): ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun getToken() {
        _token.value = getTokenUseCase.execute()
    }

    fun addTask(taskInfo: TaskModelPost){
        viewModelScope.launch {
            val param = token.let {
                AddTaskUseCase.Param(
                    token = "Bearer $it",
                    taskInfo = taskInfo
                )
            }
            param.let { addTaskUseCase.execute(it) }
        }
    }
}