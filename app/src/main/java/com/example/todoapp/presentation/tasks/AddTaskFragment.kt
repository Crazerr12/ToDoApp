package com.example.todoapp.presentation.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.AddTaskUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.presentation.models.TaskModelPost

class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val userRepository = UserRepositoryImpl(SharedPrefUserStorage(requireContext()))
        val getTokenUseCase = GetTokenUseCase(userRepository)
        val addTaskUseCase = AddTaskUseCase(userRepository)
        val vm = AddTaskFragmentViewModel(getTokenUseCase, addTaskUseCase)

        val navigation = this.findNavController()

        binding.buttonAddTask.setOnClickListener{

            val category = binding.editTextCategory.text.toString()
            val date = System.currentTimeMillis() / 1000
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val taskInfo = TaskModelPost(
                category = category,
                title = title,
                description = description,
                date = date,
                isCompleted = false
            )

            vm.addTask(taskInfo)
            Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT)
                .show()
            navigation.popBackStack()
        }
        return binding.root
    }
}