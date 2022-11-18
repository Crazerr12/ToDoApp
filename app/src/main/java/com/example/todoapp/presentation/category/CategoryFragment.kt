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

//TODO 3) Теперь осталось отобразить нужные категории, в зависимости от названия таба
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
        val adapter = TasksAdapter { idDelete ->
            idDelete.let {
                RetrofitInstance.retrofit.deleteTask("Bearer $token", it.toString())
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "task delete", Toast.LENGTH_SHORT)
                                    .show()
                            }
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
                    //TODO 4) тут можно написать какой нибудь код на 2 строки
                    adapter.submitList(tasks!!)
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