package com.example.todoapp.presentation.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.presentation.common.Validator


class LoginFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        val validator = Validator()
        val navigation = this.findNavController()

        binding.buttonSignIn.setOnClickListener {

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val email: String = binding.editTextEmail.text.toString()

            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutConfirmPassword.error =
                validator.passwordValid(binding.editTextConfirmPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutConfirmPassword.error == null) {
                toastShow(string = getString(R.string.success))
                editor.putString("EMAIL", email)
                editor.putBoolean("REMEMBER", true)
                editor.apply()
                navigation.navigate(R.id.action_loginFragment_to_switchFragment)
            }
        }

        binding.textSignUp.setOnClickListener {
            navigation.navigate(R.id.action_loginFragment_to_registerFragment)
        }

       return (binding.root)
    }

    fun toastShow(string: String) =
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()

}
