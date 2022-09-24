package com.example.todoapp.presentation.category

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentCategoryBinding
import com.example.todoapp.presentation.api.ApiService
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.TaskModel
import com.example.todoapp.presentation.tasks.TasksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*


class CategoryFragment(private val position: Int) : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        val token = preferences.getString("TOKEN", "")

        RetrofitInstance.retrofit.getTodos("Bearer $token").enqueue(object :
            Callback<List<TaskModel>>{
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if(response.isSuccessful){
                    val tasks = listOf(
                        TaskModel(
                            id = id + 1,
                            category = "work",
                            title = "first"  ,
                            description = "Have Lunch by 2pm",
                            date = LocalDate.now().toEpochDay(),
                            isCompleted = true
                        ),
                        TaskModel(
                            id = id + 1,
                            category = "work",
                            title = "second"  ,
                            description = "Have Lunch by 2pm",
                            date = LocalDate.now().toEpochDay(),
                            isCompleted = true
                        ),
                        TaskModel(
                            id = id + 1,
                            category = "work",
                            title = "third"  ,
                            description = "Have Lunch by 2pm",
                            date = LocalDate.now().toEpochDay(),
                            isCompleted = true
                        )
                    )
                    val adapter = TasksAdapter()
                    binding.recyclerAdapter.adapter = adapter
                    adapter.submitList(tasks)

                    binding.buttonAdd.setOnClickListener {
                        adapter.addItem(
                            TaskModel(
                                id = id + 1,
                                category = "work",
                                title = "fourth"  ,
                                description = "Have Lunch by 2pm",
                                date = LocalDate.now().toEpochDay(),
                                isCompleted = true
                            )
                        )
                    }
                }

            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return binding.root
    }
}