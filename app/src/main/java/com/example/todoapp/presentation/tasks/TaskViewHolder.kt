package com.example.todoapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.presentation.models.TaskModel
import java.text.SimpleDateFormat
import java.util.*

class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(task: TaskModel) = with(binding) {
        textViewTaskDescription.text = task.description
        textClockTimeDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(task.date))
        checkBox.isChecked = task.isCompleted
    }
}