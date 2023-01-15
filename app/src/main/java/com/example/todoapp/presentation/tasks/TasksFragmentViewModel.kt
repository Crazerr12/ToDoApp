package com.example.todoapp.presentation.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import kotlinx.coroutines.launch

class TasksFragmentViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getTasksUseCase: GetTasksUseCase
): ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token
    private val _categoryTasks: MutableLiveData<MutableList<String>> =
        MutableLiveData<MutableList<String>>()
    val categoryTasks = _categoryTasks

    fun getToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase.execute()
        }
    }

    fun getListOfCategoryTasks() {
        viewModelScope.launch {
            val tasks = getTasksUseCase.execute("Bearer $token")
            _categoryTasks.value = tasks.map { it.category }.toMutableSet().toMutableList()
        }
    }
}