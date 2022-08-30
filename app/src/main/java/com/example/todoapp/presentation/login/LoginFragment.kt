package com.example.todoapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todoapp.presentation.profile.ProfileActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.presentation.register.RegisterActivity
import com.example.todoapp.presentation.others.Validator


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        val validator = Validator()

        binding.buttonSignIn.setOnClickListener {

            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutConfirmPassword.error =
                validator.passwordValid(binding.editTextConfirmPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutConfirmPassword.error == null) {
                toastShow(string = getString(R.string.success))

            }
        }

        binding.textSignUp.setOnClickListener {

        }

       return (binding.root)
    }

    fun toastShow(string: String) =
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()

}
