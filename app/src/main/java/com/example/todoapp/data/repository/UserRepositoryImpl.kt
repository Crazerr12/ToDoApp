package com.example.todoapp.data.repository

import android.graphics.*
import com.example.todoapp.data.storage.UserStorage
import com.example.todoapp.domain.repository.UserRepository
import com.example.todoapp.data.api.RetrofitInstance
import com.example.todoapp.domain.usecases.*
import com.example.todoapp.presentation.models.TaskModelGet
import com.example.todoapp.presentation.models.TokenModel
import com.example.todoapp.presentation.models.UserInfoModel
import okhttp3.ResponseBody
import java.io.InputStream

class  UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {


    override suspend fun userLogin(param: LoginByEmailUseCase.Param): TokenModel? {
        return RetrofitInstance.retrofit.login(param).body()
    }

    override suspend fun userRegister(param: RegisterByEmailUseCase.Param): TokenModel? {
        return RetrofitInstance.retrofit.registration(param).body()
    }

    override fun getToken(): String? {
        return userStorage.getToken()
    }

    override fun saveToken(param: SaveTokenUseCase.Param) {
        userStorage.saveToken(param)
    }

    override suspend fun getUserInfo(param: String): UserInfoModel {
        return RetrofitInstance.retrofit.getUserInfo("Bearer $param").body()!!
    }

    override suspend fun getUserImage(param: GetUserImageUseCase.Param): Bitmap? {
        return BitmapFactory.decodeStream(RetrofitInstance.retrofit.getImage("Bearer ${param.token}", param.imageId).body()!!.byteStream())
    }

    override suspend fun getTasks(param: String): List<TaskModelGet> {
        return RetrofitInstance.retrofit.getTodos("Bearer $param").body()!!
    }

    override suspend fun addTask(param: AddTaskUseCase.Param): Unit? {
        return RetrofitInstance.retrofit.addTask("Bearer $param", param.taskInfo).body()
    }

    override suspend fun deleteTask(param: DeleteTaskUseCase.Param): Unit? {
        return RetrofitInstance.retrofit.deleteTask("Bearer ${param.token}", param.taskId).body()
    }

    override suspend fun putCheckBox(param: DeleteTaskUseCase.Param): Unit? {
        return RetrofitInstance.retrofit.putCheckbox("Bearer ${param.token}", param.taskId).body()
    }

    override suspend fun putUserImage(param: PutUserImageUseCase.Param): Unit? {
        return RetrofitInstance.retrofit.postImage("Bearer ${param.token}", param.uploadedFile).body()
    }

    override fun getRoundedBitmap(param: Bitmap): Bitmap? {
        val w = param.width
        val h = param.height
        val cx = w / 2
        val cy = h / 2

        val radius =
            if (w > h)
                h / 4
            else
                w / 4

        val output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, param.width, param.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(param, rect, rect, paint)
        param.recycle()
        return output
    }

    override fun exitFromAccount() {
        userStorage.exitFromAccount()
    }

}