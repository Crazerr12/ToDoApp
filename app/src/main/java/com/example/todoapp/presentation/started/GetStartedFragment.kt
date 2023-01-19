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

class GetStartedFragment : Fragment() {

    //Context существует, когда уже создана view
    lateinit var binding: FragmentGetStartedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        val userStorage = SharedPrefUserStorage(requireContext())
        val userRepository = UserRepositoryImpl(userStorage)
        val getTokenUseCase = GetTokenUseCase(userRepository)
        val vm = GetStartedFragmentViewModel(getTokenUseCase)
        val navigation = this.findNavController()

        vm.token.observe(viewLifecycleOwner) {
            if (it != null) {
                navigation.navigate(R.id.switchFragment)
                Toast.makeText(requireContext(), "Токен получен", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Токен не получен", Toast.LENGTH_SHORT).show()
        }

        binding.buttonGetStarted.setOnClickListener {
            navigation.navigate(R.id.action_getStartedFragment_to_loginFragment)
        }

        return binding.root
    }
}