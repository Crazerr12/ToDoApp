package com.example.todoapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment =
        if (position == 0){
            WorkCategoryFragment()
        } else {
             CategoryFragment(position)
        }
}