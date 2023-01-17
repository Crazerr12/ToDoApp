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
    private val getTokenUseCase: GetTokenUseCase,
    private val getUserImageUseCase: GetUserImageUseCase,
    private val getRoundedBitmapUseCase: GetRoundedBitmapUseCase,
    private val exitFromAccountUseCase: ExitFromAccountUseCase,
    private val putUserImage: PutUserImageUseCase
) : ViewModel() {

    private var _userInfo = MutableLiveData<UserInfoModel>()
    val userInfo: LiveData<UserInfoModel> = _userInfo
    private var _token: MutableLiveData<String?> = MutableLiveData<String?>()
    /* TODO
    *   private var _token = getTokenUseCase.execute()
    *   токен лучше хранить в обычной переменной, и как VM создается получать его
    */
    val token: LiveData<String?> = _token
    private var _image: MutableLiveData<Bitmap> = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    fun getToken() {
        _token.value = getTokenUseCase.execute()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val token = "Bearer ${_token.value}"
            _userInfo.value = getUserInfoUseCase.execute(token)
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val param = GetUserImageUseCase.Param(
                "Bearer ${_token.value}",
                _userInfo.value!!.imageId //Здесь value = null, возможно из-за viewModelScope
            )                                     //в функции getUserInfo
            val image = getUserImageUseCase.execute(param)
            _image.value = getRoundedBitmapUseCase.execute(
                Bitmap.createScaledBitmap(
                    image!!,
                    150,
                    150,
                    false
                )
            )
        }
    }

    fun setImage(context: Context, imageUri: Uri?) {
        viewModelScope.launch {
            val param = PutUserImageUseCase.Param(
                token = "Bearer $token",
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