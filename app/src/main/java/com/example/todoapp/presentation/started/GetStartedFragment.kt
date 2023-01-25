package com.example.todoapp.presentation.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.databinding.FragmentGetStartedBinding
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.example.todoapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GetStartedFragment : BaseFragment() {

    override val showBottomNavigationView = false
    private val vm by viewModel<GetStartedFragmentViewModel>()
    lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGetStartedBinding.inflate(inflater, container, false)

        vm.token.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.profile_graph)
                Toast.makeText(requireContext(), "Токен получен", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Токен не получен", Toast.LENGTH_SHORT).show()
        }

        binding.buttonGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_getStartedFragment_to_loginFragment)
        }

        return binding.root
    }
}