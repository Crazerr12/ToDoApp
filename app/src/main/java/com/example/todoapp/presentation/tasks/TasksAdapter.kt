package com.example.todoapp.presentation.tasks

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding

class TasksAdapter: RecyclerView.Adapter<TaskViewHolder>() {

    private val items = mutableListOf<TaskModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount()= items.size

    fun submitList(tasks: List<TaskModel>) {
        items.clear()
        items.addAll(tasks)
        notifyDataSetChanged()
    }

    fun addItem(task: TaskModel) {
        items.add(task)
        notifyItemInserted(items.lastIndex)
    }
}