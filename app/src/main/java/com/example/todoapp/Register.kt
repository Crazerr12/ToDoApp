package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        val validator = Validator()

        binding.buttonRegister.setOnClickListener{
            binding.inputLayoutFullName.error =
                validator.nameValid(binding.editTextFullName.text)
            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutEnterPassword.error =
                validator.passwordValid(binding.editTextEnterPassword.text)
            binding.inputLayoutConfirmPassword.error =
                validator.passwordValid(binding.editTextConfirmPassword.text)

            if(binding.editTextEnterPassword.text.toString().equals(binding.editTextConfirmPassword.text.toString())) null
            else {
                binding.inputLayoutEnterPassword.error = "password not to match"
                binding.inputLayoutConfirmPassword.error = "password not to match"
            }

            if (binding.inputLayoutFullName.error == null && binding.inputLayoutEmail.error == null &&
                binding.inputLayoutEnterPassword.error == null && binding.inputLayoutConfirmPassword.error == null){
                    val intent = Intent(this, Profile::class.java)
                intent.putExtra("email", binding.editTextEmail.text.toString())
                startActivity(intent)
            }
        }

        binding.textSignIn.setOnClickListener{
            val intent1 = Intent(this, Login::class.java)
            startActivity(intent1)
        }
        setContentView(binding.root)
    }
}