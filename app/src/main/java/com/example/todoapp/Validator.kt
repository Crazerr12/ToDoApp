package com.example.todoapp

import android.text.Editable

class Validator {
    fun emailValid(text: Editable?) =
        if (text?.isEmpty() == false && text.length!! >=7 && text.contains("@")) null
        else "not enough symbols"

    fun passwordValid(text: Editable?) =
        if (text?.isEmpty() == false && text.length!! >8) null
        else "not enough symbols"
}