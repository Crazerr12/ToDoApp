package com.example.todoapp

import android.text.Editable
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class Validator {
    fun emailValid(text: Editable?) =
        if ( text?.isEmpty() == false && text.length!! >=7 && text.contains("@") ) null
        else "not enough symbols"

    fun passwordValid(text: Editable?) =
        if ( text?.isEmpty() == false && text.length!! > 8 ) null
        else "not enough symbols"
}