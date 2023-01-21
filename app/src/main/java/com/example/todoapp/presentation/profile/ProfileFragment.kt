package com.example.todoapp.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private val vm by viewModel<ProfileFragmentViewModel>()
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
        vm.getUserInfo()
        vm.userInfo.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.collapsingToolbar.title = "Welcome ${it.email}"
// TODO после того как пришла информация о пользователе, делаем запрос на получение картинки
                vm.getImage()
            }
        }
        vm.image.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.imageUserPhoto.setImageBitmap(it)
            }
        }

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
                binding.imageUserPhoto.setImageURI(null)

                if (imageUri != null) {
                    binding.imageUserPhoto.setImageURI(imageUri)
                    vm.sendImage(requireContext(), imageUri)
                }
            }

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getContent.launch("image/\\*")
            } else {
                Toast.makeText(requireContext(), "you don't have permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.imageUserPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getContent.launch("image/\\*")
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
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
}