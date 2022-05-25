package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val validator = Validator()

        binding.buttonSignIn.setOnClickListener {

            binding.inputLayoutEmail.error =
                validator.emailValid(binding.editTextEmail.text)
            binding.inputLayoutConfirmPassword.error =
                validator.passwordValid(binding.editTextConfirmPassword.text)

            if (binding.inputLayoutEmail.error == null && binding.inputLayoutConfirmPassword.error == null) {
                toastShow(string = getString(R.string.success))
                val intent = Intent(this, ProfileFragment::class.java)
                intent.putExtra("email", binding.editTextEmail.text.toString())
                startActivity(intent)
            }
        }

        binding.textSignUp.setOnClickListener {
            val intent1 = Intent(this, RegisterActivity::class.java)
            startActivity(intent1)
        }

        setContentView(binding.root)
    }

    fun toastShow(string: String) =
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()

}
