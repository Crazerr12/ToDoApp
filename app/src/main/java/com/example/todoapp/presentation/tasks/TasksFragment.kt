package com.example.todoapp.presentation.tasks

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.presentation.api.RetrofitInstance
import com.example.todoapp.presentation.models.TaskModelGet
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragment : Fragment() {

    lateinit var preferences: SharedPreferences
    lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        preferences = requireActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        val token = preferences.getString("TOKEN", null)

        RetrofitInstance.retrofit.getTodos("Bearer $token").enqueue(object :
            Callback<List<TaskModelGet>> {
            override fun onResponse(
                call: Call<List<TaskModelGet>>,
                response: Response<List<TaskModelGet>>
            ) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    val categoryList = tasks!!.toMutableSet().map { it.category }
                    //TODO 1) Список уникальных категорий получили, теперь формируем необходимые табы через цикл
                    for (value in categoryList) {
                        binding.tabLayoutFragment.addTab(//добавляет таб в список табов
                            binding.tabLayoutFragment.newTab()//создает новый там
                                .setText(value)//даем название из списка
                        )
                    }

                    binding.viewPager2.adapter = PagerAdapter(
                        this@TasksFragment, //передаем этот фрагмент
                        binding.tabLayoutFragment.tabCount,//передаем колличество созданных табов
                        categoryList//передаем список категорий, по другому можно сказать, что это теперь список названий табов
                    )

                    TabLayoutMediator(
                        binding.tabLayoutFragment,
                        binding.viewPager2
                    ) { tab, position ->
                        tab.text =
                            when (position) {
                                position -> categoryList[position]
                                else -> null
                            }
                    }.attach()
                }
            }

            override fun onFailure(call: Call<List<TaskModelGet>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure ${t.message}")
            }
        })

        return binding.root
    }
}