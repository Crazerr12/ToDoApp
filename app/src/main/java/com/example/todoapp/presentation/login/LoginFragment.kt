package com.example.todoapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.domain.usecases.SaveTokenUseCase
import com.example.todoapp.domain.usecases.LoginByEmailUseCase
import com.example.todoapp.presentation.extensions.emailValid
import com.example.todoapp.presentation.extensions.passwordValid

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val userStorage = SharedPrefUserStorage(requireContext())
        val userRepository = UserRepositoryImpl(userStorage)
        val sendLoginUseCase = LoginByEmailUseCase(userRepository)
        val saveTokenUseCase = SaveTokenUseCase(userRepository)
        val vm = LoginFragmentViewModel(sendLoginUseCase, saveTokenUseCase)
        val navigation = this.findNavController()

        binding.buttonSignIn.setOnClickListener {

            binding.inputLayoutEmail.error = emailValid(binding.editTextEmail.text)
            binding.inputLayoutConfirmPassword.error =
                passwordValid(binding.editTextConfirmPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutConfirmPassword.error == null) {

                val userLogin = LoginByEmailUseCase.Param(
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextConfirmPassword.text.toString()
                )

                vm.userLogin(userLogin)
            }
        }

        vm.token.observe(viewLifecycleOwner) {
            if (it != null) {
                navigation.navigate(R.id.action_loginFragment_to_switchFragment)
                Toast.makeText(requireContext(), "Токен получен", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Токен не получен", Toast.LENGTH_SHORT).show()
        }

        binding.textSignUp.setOnClickListener {
            navigation.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return (binding.root)
    }
}