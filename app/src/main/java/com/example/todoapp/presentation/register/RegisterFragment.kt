package com.example.todoapp.presentation.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.presentation.common.Validator
import com.example.todoapp.databinding.FragmentRegisterBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.RegistrationModel
import com.example.todoapp.presentation.models.TokenModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                val userRegistration = RegistrationModel(
                    name = binding.editTextFullName.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextEnterPassword.text.toString()
                )
                RetrofitInstance.retrofit.registration(userRegistration).enqueue(object : Callback<TokenModel>{
                    override fun onResponse(
                        call: Call<TokenModel>,
                        response: Response<TokenModel>
                    ) {
                        if (response.isSuccessful){
                            val token = response.body()?.token ?: "No token"
                            editor.putString("TOKEN", token)
                        }
                    }

                    override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })

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