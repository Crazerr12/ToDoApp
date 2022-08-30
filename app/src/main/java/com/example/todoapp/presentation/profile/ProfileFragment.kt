package com.example.todoapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding
import com.example.todoapp.presentation.login.LoginFragment

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val email = requireActivity().intent.getStringExtra("email")
        binding.collapsingToolbar.title = "Welcome $email"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.appBar.setOnMenuItemClickListener() {
            when (it.itemId) {
                R.id.exit -> startActivity(Intent(activity, LoginFragment::class.java))
            }
            true
        }
    }
}