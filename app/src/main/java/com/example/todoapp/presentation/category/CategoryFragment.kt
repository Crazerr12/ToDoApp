package com.example.todoapp.presentation.category

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCategoryBinding
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.DeleteTaskUseCase
import com.example.todoapp.domain.usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.domain.usecases.PutCheckBoxUseCase
import com.example.todoapp.presentation.tasks.TasksAdapter

class CategoryFragment(private val position: Int, private val category: List<String>) : Fragment() {

    lateinit var binding: FragmentCategoryBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val userRepository = UserRepositoryImpl(SharedPrefUserStorage(requireContext()))
        val getTokenUseCase = GetTokenUseCase(userRepository)
        val getTasksUseCase = GetTasksUseCase(userRepository)
        val deleteTaskUseCase = DeleteTaskUseCase(userRepository)
        val putCheckBoxUseCase = PutCheckBoxUseCase(userRepository)
        val vm = CategoryFragmentViewModel(
            getTokenUseCase,
            getTasksUseCase,
            deleteTaskUseCase,
            putCheckBoxUseCase
        )

        vm.getToken()
        val navigation = this.findNavController()

        val adapter = TasksAdapter { idDelete, idMark ->
            idDelete.let {
                val param = DeleteTaskUseCase.Param(
                    token = "Bearer ${vm.token}",
                    taskId = it.toString()
                )
                vm.deleteTask(param)
                if (idMark == null)
                    Toast.makeText(
                        requireContext(),
                        "task delete",
                        Toast.LENGTH_SHORT
                    )
                        .show()
            }
            idMark.let {
                val param = DeleteTaskUseCase.Param(
                    token = "Bearer ${vm.token}",
                    taskId = it.toString()
                )
                vm.putCheckBox(param)
                if (idDelete == null)
                    Toast.makeText(
                        requireContext(),
                        "task complete",
                        Toast.LENGTH_SHORT
                    )
                        .show()
            }
        }

        binding.recyclerAdapter.adapter = adapter

        vm.getListOfTasks(position, category, adapter)

        binding.buttonAdd.setOnClickListener {
            navigation.navigate(R.id.action_switchFragment_to_addTaskFragment)
        }

        return binding.root
    }
}