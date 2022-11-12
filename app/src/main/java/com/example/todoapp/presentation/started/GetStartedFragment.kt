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

    lateinit var preferences: SharedPreferences
    lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        preferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)


        val navigation = this.findNavController()

        if (token != null) {
            navigation.navigate(R.id.switchFragment)
        }

        binding.buttonGetStarted.setOnClickListener{
            navigation.navigate(R.id.action_getStartedFragment_to_loginFragment)
        }
        return (binding.root)
    }
}