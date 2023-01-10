package com.example.todoapp.presentation.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.repository.UserRepositoryImplementation
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.presentation.common.Validator
import com.example.todoapp.databinding.FragmentRegisterBinding
import com.example.todoapp.domain.usecases.RegisterByEmailUseCase
import com.example.todoapp.presentation.models.RegistrationModel

class RegisterFragment : Fragment() {

    private val userStorage = SharedPrefUserStorage(requireContext())
    private val userRepository = UserRepositoryImplementation(userStorage)
    private val registerByEmailUseCase = RegisterByEmailUseCase(userRepository)
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navigation = this.findNavController()
        val validator = Validator()

        binding.buttonRegister.setOnClickListener {

            binding.inputLayoutFullName.error =
                validator.nameValid(binding.editTextFullName.text)
            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutEnterPassword.error =
                validator.checkPassword(
                    binding.editTextEnterPassword.text,
                    binding.editTextConfirmPassword.text
                )
            binding.inputLayoutConfirmPassword.error =
                validator.checkPassword(
                    binding.editTextConfirmPassword.text,
                    binding.editTextEnterPassword.text
                )

            if (binding.inputLayoutFullName.error == null && binding.inputLayoutEmail.error == null &&
                binding.inputLayoutEnterPassword.error == null && binding.inputLayoutConfirmPassword.error == null
            ) {
                val userRegistration = RegistrationModel(
                    name = binding.editTextFullName.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextEnterPassword.text.toString()
                )

                registerByEmailUseCase.execute(userRegistration, navigation)
            }
        }

        binding.textSignIn.setOnClickListener {
            navigation.popBackStack()
        }
    }
}