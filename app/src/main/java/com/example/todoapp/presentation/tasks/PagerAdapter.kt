package com.example.todoapp.presentation.tasks

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todoapp.presentation.category.CategoryFragment
import com.example.todoapp.presentation.models.TaskModelGet

class PagerAdapter(
    fragment: Fragment,
    private val tabCounter: Int,
    private val category: List<String> //TODO 2) не много тип данных поменял
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = tabCounter

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment(position, category)// сюда передаем список названий табов
    }
}