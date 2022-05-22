package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        val email = intent.getStringExtra("email")

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ProfileFragment(email))
                .commit()

        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menuFragmentProfile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, ProfileFragment(email))
                        .commit()
                    true
                }
                R.id.menuFragmentTasks -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, TasksFragment(email))
                        .commit()
                    true
                }
                else -> true
            }
        }
    }
}