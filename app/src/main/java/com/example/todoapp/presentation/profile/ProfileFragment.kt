package com.example.todoapp.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.*


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userRepository = UserRepositoryImpl(SharedPrefUserStorage(requireContext()))
        val getUserInfoUseCase = GetUserInfoUseCase(userRepository)
        val getTokenUseCase = GetTokenUseCase(userRepository)
        val getUserImageUseCase = GetUserImageUseCase(userRepository)
        val getRoundedBitmapUseCase = GetRoundedBitmapUseCase(userRepository)
        val exitFromAccountUseCase = ExitFromAccountUseCase(userRepository)
        val putUserImageUseCase = PutUserImageUseCase(userRepository)
        val vm = ProfileFragmentViewModel(
            getUserInfoUseCase,
            getTokenUseCase,
            getUserImageUseCase,
            getRoundedBitmapUseCase,
            exitFromAccountUseCase,
            putUserImageUseCase
        )

        vm.getToken()
        vm.getUserInfo()
        vm.userInfo.observe(viewLifecycleOwner) {
            binding.collapsingToolbar.title = "Welcome ${it?.get(0)}"
        }
        vm.getImage()
        vm.image.observe(viewLifecycleOwner) {
            binding.imageUserPhoto.setImageBitmap(it)
        }

        binding.imageUserPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getContent(vm)
            } else {
                requestPermissionLauncher(vm)
            }
        }

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    vm.exitFromAccount()
                    this.findNavController().navigate(R.id.loginFragment)
                }
            }
            true
        }
    }

    private fun requestPermissionLauncher(vm: ProfileFragmentViewModel){
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getContent(vm)
            } else {
                Toast.makeText(requireContext(), "you don't have permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun getContent(vm: ProfileFragmentViewModel){
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            binding.imageUserPhoto.setImageURI(null)
            binding.imageUserPhoto.setImageURI(imageUri)
            vm.setImage(requireContext(), imageUri)
        }.launch("image/\\*")
    }
}