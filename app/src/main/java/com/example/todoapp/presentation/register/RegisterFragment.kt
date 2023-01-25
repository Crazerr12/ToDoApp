package com.example.todoapp.presentation.register

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
import com.example.todoapp.databinding.FragmentRegisterBinding
import com.example.todoapp.domain.usecases.RegisterByEmailUseCase
import com.example.todoapp.domain.usecases.SaveTokenUseCase
import com.example.todoapp.presentation.base.BaseFragment
import com.example.todoapp.presentation.extensions.checkPassword
import com.example.todoapp.presentation.extensions.emailValid
import com.example.todoapp.presentation.extensions.nameValid
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {

    override val showBottomNavigationView = false
    private val vm by viewModel<RegisterFragmentViewModel>()
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonRegister.setOnClickListener {

            binding.inputLayoutFullName.error = nameValid(binding.editTextFullName.text)
            binding.inputLayoutEmail.error = emailValid(binding.editTextEmail.text)
            binding.inputLayoutEnterPassword.error = checkPassword(
                    binding.editTextEnterPassword.text,
                    binding.editTextConfirmPassword.text
                )
            binding.inputLayoutConfirmPassword.error = checkPassword(
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
                findNavController().navigate(R.id.action_registerFragment_to_profile_graph)
                Toast.makeText(requireContext(), "Токен получен", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Токен не получен", Toast.LENGTH_SHORT).show()
        }

        binding.textSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}