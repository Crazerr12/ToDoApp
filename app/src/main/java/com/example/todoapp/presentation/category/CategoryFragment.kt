package com.example.todoapp.presentation.category

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentCategoryBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.TaskModel
import com.example.todoapp.presentation.tasks.TasksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        preferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", "")
        val adapter = TasksAdapter()
        binding.recyclerAdapter.adapter = adapter

        RetrofitInstance.retrofit.getTodos("Bearer $token").enqueue(object :
            Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    adapter.submitList(tasks!!)
                }

            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.message}")
            }
        })

        val taskInfo = TaskModel(
            category = "string",
            title = "string",
            description = "Have Lunch by 2pm",
            date = 100,
            isCompleted = false
        )

        binding.buttonAdd.setOnClickListener {

            RetrofitInstance.retrofit.addTask("Bearer $token", taskInfo)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT)
                                .show()
                            RetrofitInstance.retrofit.getTodos("Bearer $token").enqueue(object :
                                Callback<List<TaskModel>> {
                                override fun onResponse(
                                    call: Call<List<TaskModel>>,
                                    response: Response<List<TaskModel>>
                                ) {
                                    if (response.isSuccessful) {
                                        val tasks = response.body()
                                        adapter.submitList(tasks!!)
                                    }

                                }

                                override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                                    Log.e(TAG, "onFailure ${t.message}")
                                }
                            })
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.e(TAG, "onFailure ${t.message}")
                    }

                })
        }

        return binding.root
    }
}