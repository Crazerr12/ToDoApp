package com.example.todoapp.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.todoapp.presentation.managers.BottomNavigationViewManager
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment : Fragment() {
    open val showBottomNavigationView: Boolean
        get() = (parentFragment as? BaseFragment)?.showBottomNavigationView ?: false

    private var bottomNavigationViewManager: BottomNavigationViewManager? = null
    protected fun setBottomNavigationViewVisibility(isVisible: Boolean) {
        bottomNavigationViewManager?.setNavigationViewVisibility(isVisible)
    }

    override fun onAttach(context: Context) {
        if (context is BottomNavigationViewManager)
            bottomNavigationViewManager = context
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewManager?.setNavigationViewVisibility(showBottomNavigationView)
    }
}