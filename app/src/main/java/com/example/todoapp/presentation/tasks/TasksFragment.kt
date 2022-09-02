package com.example.todoapp.presentation.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTasksBinding
import com.google.android.material.tabs.TabLayoutMediator

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        binding.viewPager2.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabLayoutFragment, binding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.Work)
                1 -> getString(R.string.Entertainments)
                2 -> getString(R.string.Study)
                else -> null
            }
        }.attach()
        return binding.root
    }
}