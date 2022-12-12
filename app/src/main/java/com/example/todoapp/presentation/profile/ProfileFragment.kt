package com.example.todoapp.presentation.profile

import URIPathHelper
import android.Manifest
import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
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
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.UserInfoModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : Fragment() {

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
        var height = binding.imageUserPhoto.maxHeight
        var width = binding.imageUserPhoto.maxWidth
        val token = preferences.getString("TOKEN", "")
        val imageId = preferences.getString("IMAGE_ID", "")
        val editor: SharedPreferences.Editor = preferences.edit()

        RetrofitInstance.retrofit.getInfo("Bearer $token").enqueue(object :
            Callback<UserInfoModel> {
            override fun onResponse(
                call: Call<UserInfoModel>,
                response: Response<UserInfoModel>
            ) {
                if (response.isSuccessful) {
                    val name = response.body()?.name
                    editor.putString("IMAGE_ID", response.body()?.imageId)
                    editor.apply()
                    binding.collapsingToolbar.title = "Welcome $name"
                }

            }

            override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure ${t.message}")
            }
        })

        RetrofitInstance.retrofit.getImage("Bearer $token", imageId!!)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!.byteStream()
                        val bitmapImage = BitmapFactory.decodeStream(body)

                        binding.imageUserPhoto.setImageBitmap(bitmapImage)

                        height = 100
                        width = 100
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("secondLog", "chto-to ne tak")
                }
            })

        val getContent: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->

                binding.imageUserPhoto.setImageURI(null)

                val file = File(URIPathHelper().getPath(requireContext(), imageUri!!))
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("uploadedFile", file.name, requestFile)

                binding.imageUserPhoto.setImageURI(imageUri)

                RetrofitInstance.retrofit.postImage("Bearer $token", part)
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                            Log.d("Log1", "Ваше фото успешно загружено")

                            height = 100
                            width = 100
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.d("Log2", "Упс... Возникла ошибка")
                        }

                    })
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
                    editor.clear()
                    editor.apply()
                    this.findNavController().navigate(R.id.loginFragment)
                }
            }
            true
        }
    }
}