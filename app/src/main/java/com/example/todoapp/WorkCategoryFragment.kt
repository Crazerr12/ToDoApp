package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentCategoryWorkBinding
import java.util.Calendar.getInstance


class WorkCategoryFragment: Fragment() {

    lateinit var binding: FragmentCategoryWorkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryWorkBinding.inflate(inflater, container, false)

        val tasks = listOf(
            Task(
                task_desc = "Have Lunch by 2pm",
                time_date = getInstance()
            ),
            Task(
                task_desc = "Have Lunch by 24pm",
                time_date = getInstance()
            ),
            Task(
                task_desc = "Have Lunch by 23pm",
                time_date = getInstance()
            )
        )
        val adapter = TasksAdapter()
        binding.recyclerAdapter.adapter = adapter
        adapter.submitList(tasks)

        binding.buttonAdd.setOnClickListener{
            adapter.addItem(
                Task(
                    task_desc = "Have Lunch by 2pm",
                    time_date = getInstance()
                )
            )
        }

        return binding.root
    }
}