package com.example.todoapp.presentation.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSwitchBinding
import com.example.todoapp.presentation.tasks.TasksFragment

class SwitchFragment: Fragment() {
    lateinit var binding: FragmentSwitchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwitchBinding.inflate(inflater, container, false)
        return(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menuFragmentProfile -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, ProfileFragment())
                        .commit()
                    true
                }
                R.id.menuFragmentTasks -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, TasksFragment())
                        .commit()
                    true
                }
                else -> true
            }
        }
    }
}