package com.example.todoapp.presentation.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentGetStartedBinding

class GetStartedFragment : Fragment() {

    lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        return (binding.root)
    }
}