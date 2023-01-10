package com.example.todoapp.presentation.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.repository.UserRepositoryImplementation
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.domain.usecases.SendLoginUseCase
import com.example.todoapp.presentation.common.Validator
import com.example.todoapp.presentation.models.LoginModel

class LoginFragment : Fragment() {

    private val userStorage = SharedPrefUserStorage(requireContext())
    private val userRepository = UserRepositoryImplementation(userStorage)
    private val sendLoginUseCase = SendLoginUseCase(userRepository)
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

            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutConfirmPassword.error =
                validator.passwordValid(binding.editTextConfirmPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutConfirmPassword.error == null) {

                val userLogin = LoginModel(
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextConfirmPassword.text.toString()
                )

                sendLoginUseCase.execute(userLogin, navigation)
            }
        }

        binding.textSignUp.setOnClickListener {
            navigation.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return (binding.root)
    }
}