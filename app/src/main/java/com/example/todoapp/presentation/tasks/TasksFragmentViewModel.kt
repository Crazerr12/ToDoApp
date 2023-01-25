package com.example.todoapp.presentation.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import kotlinx.coroutines.launch

class TasksFragmentViewModel(
    getTokenUseCase: GetTokenUseCase,
    private val getTasksUseCase: GetTasksUseCase
): ViewModel() {


    private val token = getTokenUseCase.execute()
    private val _categoryTasks: MutableLiveData<MutableList<String>> =
        MutableLiveData<MutableList<String>>()
    val categoryTasks = _categoryTasks

    fun getListOfCategoryTasks() {
        viewModelScope.launch {
            val tasks = getTasksUseCase.execute(token!!)
            _categoryTasks.value = tasks.map { it.category }.toMutableSet().toMutableList()
        }
    }
}