package com.example.todoapp.presentation.started

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentGetStartedBinding
import com.example.todoapp.databinding.FragmentLoginBinding

class GetStartedFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentGetStartedBinding
    var isRemember = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemember = sharedPreferences.getBoolean("REMEMBER", false)

        val navigation = this.findNavController()

        if (isRemember) {
            navigation.navigate(R.id.switchFragment)
        }

        binding.buttonGetStarted.setOnClickListener{
            navigation.navigate(R.id.action_getStartedFragment_to_loginFragment)
        }
        return (binding.root)
    }
}