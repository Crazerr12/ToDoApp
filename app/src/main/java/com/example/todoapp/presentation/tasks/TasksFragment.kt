package com.example.todoapp.presentation.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.data.repository.UserRepositoryImpl
import com.example.todoapp.data.storage.SharedPrefUserStorage
import com.example.todoapp.domain.usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.GetTokenUseCase
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {

    private val vm by viewModel<TasksFragmentViewModel>()
    lateinit var binding: FragmentTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        vm.getListOfCategoryTasks()
        vm.categoryTasks.observe(viewLifecycleOwner){
            for (value in it) {
                binding.tabLayoutFragment.addTab(//добавляет таб в список табов
                    binding.tabLayoutFragment.newTab()//создает новый там
                        .setText(value)//даем название из списка
                )
            }

            binding.viewPager2.adapter = PagerAdapter(
                this@TasksFragment, //передаем этот фрагмент
                binding.tabLayoutFragment.tabCount,//передаем колличество созданных табов
                it//передаем список категорий, по другому можно сказать, что это теперь список названий табов
            )

            TabLayoutMediator(
                binding.tabLayoutFragment,
                binding.viewPager2
            ) { tab, position ->
                tab.text =
                    when (position) {
                        position -> it[position]
                        else -> null
                    }
            }.attach()
        }
        return binding.root
    }
}