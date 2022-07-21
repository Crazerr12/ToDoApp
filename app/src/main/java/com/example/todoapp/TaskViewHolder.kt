package com.example.todoapp

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding

class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task) = with(binding) {
        textViewTaskDescription.text = task.task_desc
        textClockTimeDate.text = task.time_date.toString()
    }
}