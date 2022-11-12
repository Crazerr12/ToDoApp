package com.example.todoapp.presentation.profile

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.RegistrationModel
import com.example.todoapp.presentation.models.TaskModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val token = preferences.getString("TOKEN", "")

        RetrofitInstance.retrofit.getInfo("Bearer $token").enqueue(object :
            Callback<RegistrationModel> {
            override fun onResponse(
                call: Call<RegistrationModel>,
                response: Response<RegistrationModel>
            ) {
                if (response.isSuccessful) {
                    val name = response.body()?.name
                    binding.collapsingToolbar.title = "Welcome $name"
                }

            }

            override fun onFailure(call: Call<RegistrationModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure ${t.message}")
            }
        })

        val editor: SharedPreferences.Editor = preferences.edit()

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