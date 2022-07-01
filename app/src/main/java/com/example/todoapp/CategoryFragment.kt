package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentCategoryBinding


class CategoryFragment(private val position :Int) : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.textView.text = position.toString()

        when (position) {
            0 -> binding.textView.text = getString(R.string.Work)
            1 -> binding.textView.text = getString(R.string.Entertainments)
            2 -> binding.textView.text = getString(R.string.Study)
            else -> binding.textView.text = position.toString()
        }
        return binding.root
    }
}