package com.example.todoapp

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

    fun checkPassword(textEnter: Editable?, textConfirm: Editable?) =
        if (textEnter.toString().equals(textConfirm.toString())) null
        else "password not to match"
}