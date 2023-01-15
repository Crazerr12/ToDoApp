package com.example.todoapp.presentation.register

import android.content.Context
import android.content.SharedPreferences
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
import com.example.todoapp.presentation.common.Validator
import com.example.todoapp.databinding.FragmentRegisterBinding
import com.example.todoapp.domain.usecases.RegisterByEmailUseCase
import com.example.todoapp.domain.usecases.SaveTokenUseCase

class RegisterFragment : Fragment() {

    private val userStorage = SharedPrefUserStorage(requireContext())
    private val userRepository = UserRepositoryImpl(userStorage)
    private val registerByEmailUseCase = RegisterByEmailUseCase(userRepository)
    private val saveTokenUseCase = SaveTokenUseCase(userRepository)
    private var vm: RegisterFragmentViewModel =
        RegisterFragmentViewModel(registerByEmailUseCase, saveTokenUseCase)
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
                val userRegistration = RegisterByEmailUseCase.Param(
                    name = binding.editTextFullName.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextEnterPassword.text.toString()
                )
                vm.userRegister(userRegistration)
            }
        }

        vm.token.observe(viewLifecycleOwner) {
            if (it != null) {
                navigation.navigate(R.id.action_loginFragment_to_switchFragment)
                Toast.makeText(requireContext(), "Токен получен", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Токен не получен", Toast.LENGTH_SHORT).show()
        }

        binding.textSignIn.setOnClickListener {
            navigation.popBackStack()
        }
    }
}