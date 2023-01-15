package com.example.todoapp.presentation.profile

import URIPathHelper
import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding
import com.example.todoapp.data.api.RetrofitInstance
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileFragment : Fragment() {

    private var userRepository = UserRepositoryImpl(SharedPrefUserStorage(requireContext()))
    private var getUserInfoUseCase = GetUserInfoUseCase(userRepository)
    private var getTokenUseCase = GetTokenUseCase(userRepository)
    private val getUserImageUseCase = GetUserImageUseCase(userRepository)
    private val getRoundedBitmapUseCase = GetRoundedBitmapUseCase(userRepository)
    private val exitFromAccountUseCase = ExitFromAccountUseCase(userRepository)
    private val putUserImageUseCase = PutUserImageUseCase(userRepository)
    private var vm = ProfileFragmentViewModel(
        getUserInfoUseCase,
        getTokenUseCase,
        getUserImageUseCase,
        getRoundedBitmapUseCase,
        exitFromAccountUseCase,
        putUserImageUseCase
    )
    lateinit var preferences: SharedPreferences
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = requireActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
                getContent()
            } else {
                requestPermissionLauncher()
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

    private fun requestPermissionLauncher(){
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getContent()
            } else {
                Toast.makeText(requireContext(), "you don't have permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun getContent(){
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->

            binding.imageUserPhoto.setImageURI(null)
            binding.imageUserPhoto.setImageURI(imageUri)

            vm.setImage(requireContext(), imageUri)

        }.launch("image/\\*")
    }
}