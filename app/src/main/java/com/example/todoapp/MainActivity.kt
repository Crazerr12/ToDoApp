package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val validator = Validator()

        binding.buttonSignIn.setOnClickListener{

            val toastMessage = "Success!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, toastMessage, duration)

            binding.inputLayoutEmail.error =
            validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutPassword.error =
            validator.passwordValid(binding.editTextPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutPassword.error == null)
                toast.show()
            else null

        }

        setContentView(binding.root)
    }
}