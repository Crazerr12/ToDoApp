package com.example.todoapp.presentation.profile

import URIPathHelper
import android.content.Context
import android.graphics.*
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecases.*
import com.example.todoapp.presentation.models.UserInfoModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileFragmentViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    getTokenUseCase: GetTokenUseCase,
    private val getUserImageUseCase: GetUserImageUseCase,
    private val getRoundedBitmapUseCase: GetRoundedBitmapUseCase,
    private val exitFromAccountUseCase: ExitFromAccountUseCase,
    private val putUserImage: PutUserImageUseCase
) : ViewModel() {

    private var _userInfo = MutableLiveData<UserInfoModel>()
    val userInfo: LiveData<UserInfoModel> = _userInfo
    private val token = getTokenUseCase.execute()
    private var _image: MutableLiveData<Bitmap> = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = getUserInfoUseCase.execute(token!!)
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val param = GetUserImageUseCase.Param(
                token!!,
                _userInfo.value!!.imageId
            )
            val image = getUserImageUseCase.execute(param)
            _image.value = getRoundedBitmapUseCase.execute(image!!)
        }
    }

    fun sendImage(context: Context, imageUri: Uri?) {
        viewModelScope.launch {
            val param = PutUserImageUseCase.Param(
                token = token!!,
                uploadedFile = uriToPart(context, imageUri)
            )
            putUserImage.execute(param)
        }
    }

    private fun uriToPart(context: Context, imageUri: Uri?): MultipartBody.Part {
        val file = File(URIPathHelper().getPath(context, imageUri!!))
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("uploadedFile", file.name, requestFile)
    }

    fun exitFromAccount() {
        exitFromAccountUseCase.execute()
    }
}