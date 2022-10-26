package com.example.todoapp.presentation.profile

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var preferences: SharedPreferences
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = requireActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val email = preferences.getString("EMAIL", "")
        val editor: SharedPreferences.Editor = preferences.edit()
        binding.collapsingToolbar.title = "Welcome $email"

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    editor.clear()
                    editor.apply()
                    this.findNavController().navigate(R.id.loginFragment)
                }
            }
            true
        }
    }
}