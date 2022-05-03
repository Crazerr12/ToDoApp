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

            binding.inputLayoutEmail.error =
            validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutPassword.error =
            validator.passwordValid(binding.editTextPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutPassword.error == null)
            toastShow(string = getString(R.string.success))
        }
        setContentView(binding.root)
    }
    fun toastShow(string: String) =
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()

}
