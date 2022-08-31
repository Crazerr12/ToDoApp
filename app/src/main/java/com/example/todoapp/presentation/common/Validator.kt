package com.example.todoapp.presentation.common

import android.text.Editable

class Validator {
    fun emailValid(text: Editable?) =
        if (text?.isEmpty() == false && text.length >= 7 && text.contains("@")) null
        else "not enough symbols"

    fun passwordValid(text: Editable?) =
        if (text?.isEmpty() == false && text.length > 8) null
        else "not enough symbols"

    fun nameValid(text: Editable?) =
        if (text?.isEmpty() == false) null
        else "not enough symbols"

    fun checkPassword(text1: Editable?, text2: Editable?) =
        if (text1.toString() == text2.toString()) {
            passwordValid(text1)
        } else "password not to match"
}