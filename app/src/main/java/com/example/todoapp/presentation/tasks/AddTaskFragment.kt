package com.example.todoapp.presentation.tasks

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.TaskModelPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        val preferences = requireActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)
        val navigation = this.findNavController()

        binding.buttonAddTask.setOnClickListener{

            val date = System.currentTimeMillis() / 1000
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val taskInfo = TaskModelPost(
                category = "string",
                title = title,
                description = description,
                date = date,
                isCompleted = false
            )

            RetrofitInstance.retrofit.addTask("Bearer $token", taskInfo)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT)
                                .show()
                            navigation.popBackStack()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.e(ContentValues.TAG, "onFailure ${t.message}")
                    }

                })
        }
        return binding.root
    }
}