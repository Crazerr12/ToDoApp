package com.example.todoapp.presentation.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.presentation.models.TaskModelGet
import java.time.LocalDateTime
import java.time.Month

class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(task: TaskModelGet, clickListener: (idDelete: String?) -> Unit) = with(binding) {
        root.setOnLongClickListener {
            clickListener(
                task.id
            )
            true
        }
        textViewTaskDescription.text = task.description
        textClockTimeDate.text =
            LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0).plusSeconds(task.date).toString()
        checkBox.isChecked = task.isCompleted
    }
}