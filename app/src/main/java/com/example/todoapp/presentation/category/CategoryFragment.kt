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
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCategoryBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.TaskModelGet
import com.example.todoapp.presentation.tasks.TasksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment(private val position: Int, private val category: List<String>) : Fragment() {

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
        val navigation = this.findNavController()
        val adapter = TasksAdapter { idDelete, idMark ->
            idDelete.let {
                RetrofitInstance.retrofit.deleteTask("Bearer $token", it.toString())
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                if (idMark == null)
                                    Toast.makeText(
                                        requireContext(),
                                        "task delete",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.e(TAG, "onFailure ${t.message}")
                        }

                    })
            }
            idMark.let {
                RetrofitInstance.retrofit.putCheckbox("Bearer $token", it.toString())
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful)
                                if (idDelete == null)
                                    Toast.makeText(
                                        requireContext(),
                                        "task complete",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.e(TAG, "onFailure ${t.message}")
                        }
                    })
            }
        }

        binding.recyclerAdapter.adapter = adapter

        RetrofitInstance.retrofit.getTodos("Bearer $token").enqueue(object :
            Callback<List<TaskModelGet>> {
            override fun onResponse(
                call: Call<List<TaskModelGet>>,
                response: Response<List<TaskModelGet>>
            ) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    val list = mutableListOf<TaskModelGet>()
                    when (position) {
                        position -> {
                            for (task in tasks!!) {
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

            override fun onFailure(call: Call<List<TaskModelGet>>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.message}")
            }
        })

        binding.buttonAdd.setOnClickListener {
            navigation.navigate(R.id.action_switchFragment_to_addTaskFragment)
        }

        return binding.root
    }
}