package com.example.todoapp.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.AddTaskUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.presentation.models.TaskModelPost
import kotlinx.coroutines.launch

class AddTaskFragmentViewModel(
    getTokenUseCase: GetTokenUseCase,
    private val addTaskUseCase: AddTaskUseCase
): ViewModel() {


    private val token = getTokenUseCase.execute()

    fun addTask(taskInfo: TaskModelPost){
        viewModelScope.launch {
            val param = token.let {
                AddTaskUseCase.Param(
                    token = it!!,
                    taskInfo = taskInfo
                )
            }
            param.let { addTaskUseCase.execute(it) }
        }
    }
}