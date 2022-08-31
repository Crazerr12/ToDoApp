package com.example.todoapp.presentation.register


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.presentation.common.Validator
import com.example.todoapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navigation = this.findNavController()
        val validator = Validator()

        binding.buttonRegister.setOnClickListener {

            val email = binding.editTextEmail.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

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
                editor.putString("EMAIL", email)
                editor.putBoolean("REMEMBER", true)
                editor.apply()
                navigation.navigate(R.id.action_registerFragment_to_switchFragment)
            }
        }

        binding.textSignIn.setOnClickListener {
            navigation.popBackStack()
        }
    }
}