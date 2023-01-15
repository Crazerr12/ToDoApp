package com.example.todoapp.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.DeleteTaskUseCase
import com.example.todoapp.domain.usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.domain.usecases.PutCheckBoxUseCase
import com.example.todoapp.presentation.models.TaskModelGet
import com.example.todoapp.presentation.tasks.TasksAdapter
import kotlinx.coroutines.launch

class CategoryFragmentViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val putCheckBoxUseCase: PutCheckBoxUseCase
) : ViewModel() {

    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    val token: LiveData<String?> = _token
    private val _tasks: MutableLiveData<List<TaskModelGet>> =
        MutableLiveData<List<TaskModelGet>>()
    val tasks = _tasks

    fun getToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase.execute()
        }
    }

    fun getListOfTasks(position: Int, category: List<String>, adapter: TasksAdapter) {
        viewModelScope.launch {
            val tasks = getTasksUseCase.execute("Bearer $token")
            val list = mutableListOf<TaskModelGet>()
            when (position) {
                position -> {
                    for (task in tasks) {
                        if (category[position] == task.category) {
                            list.add(task)
                        }
                    }
                    adapter.submitList(list)
                    list.clear()
                }
            }
        }
    }

    fun deleteTask(param: DeleteTaskUseCase.Param) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(param)
        }
    }

    fun putCheckBox(param: DeleteTaskUseCase.Param) {
        viewModelScope.launch {
            putCheckBoxUseCase.execute(param)
        }
    }
}