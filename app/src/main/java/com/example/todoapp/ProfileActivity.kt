package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        binding.imageLoginOut.setOnClickListener {
            super.onBackPressed()
        }
        val email = intent.getStringExtra("email")

        binding.textWelcomeBack.text = "Welcome $email"

        setContentView(binding.root)
    }
}