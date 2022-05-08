package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.databinding.ActivityGetStartedBinding

class GetStarted : AppCompatActivity() {
    lateinit var binding: ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)

        binding.buttonGetStarted.setOnClickListener{
             val intent = Intent(this, Login::class.java)
                     startActivity (intent)
        }
        setContentView(binding.root)
    }
}