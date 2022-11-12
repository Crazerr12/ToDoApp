package com.example.todoapp.presentation.models


data class TaskModel(
    val category: String,
    val title: String,
    val description: String,
    val date: Long,
    val isCompleted: Boolean
)