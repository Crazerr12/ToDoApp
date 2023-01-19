package com.example.todoapp.presentation.category

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
    getTokenUseCase: GetTokenUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val putCheckBoxUseCase: PutCheckBoxUseCase
) : ViewModel() {

    private val token = getTokenUseCase.execute()
    private val _tasks: MutableLiveData<List<TaskModelGet>> =
        MutableLiveData<List<TaskModelGet>>()
    val tasks = _tasks

    fun getListOfTasks(position: Int, category: List<String>, adapter: TasksAdapter) {
        viewModelScope.launch {
            val tasks = getTasksUseCase.execute(token!!)
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

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            val param = DeleteTaskUseCase.Param(
                token = token!!,
                taskId = taskId
                    )
            deleteTaskUseCase.execute(param)
        }
    }

    fun putCheckBox(taskId: String) {
        viewModelScope.launch {
            val param = DeleteTaskUseCase.Param(
                token = token!!,
                taskId = taskId
            )
            putCheckBoxUseCase.execute(param)
        }
    }
}