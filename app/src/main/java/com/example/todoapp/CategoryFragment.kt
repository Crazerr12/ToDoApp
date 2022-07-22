package com.example.todoapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentCategoryBinding
import java.time.LocalDate


class CategoryFragment(private val position: Int) : Fragment() {

    lateinit var binding: FragmentCategoryBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        val tasks = listOf(
            TaskModel(
                description = "Have Lunch by 2pm",
                date = LocalDate.now().toEpochDay()
            ),
            TaskModel(
                description = "Have Lunch by 24pm",
                date = LocalDate.now().toEpochDay()
            ),
            TaskModel(
                description = "Have Lunch by 23pm",
                date = LocalDate.now().toEpochDay()
            )
        )
        val adapter = TasksAdapter()
        binding.recyclerAdapter.adapter = adapter
        adapter.submitList(tasks)

        binding.buttonAdd.setOnClickListener {
            adapter.addItem(
                TaskModel(
                    description = "Have Lunch by 2pm",
                    date = LocalDate.now().toEpochDay()
                )
            )
        }
        return binding.root
    }
}