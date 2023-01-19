package com.example.todoapp.presentation.extensions

import android.text.Editable
import androidx.fragment.app.Fragment

const val NES ="not enough symbols"

fun Fragment.emailValid(text: Editable?) =
    if (text?.isEmpty() == false && text.length >= 7 && text.contains("@")) null
    else NES

fun Fragment.passwordValid(text: Editable?) =
    if (text?.isEmpty() == false && text.length > 8) null
    else NES

fun Fragment.nameValid(text: Editable?) =
    if (text?.isEmpty() == false) null
    else NES

fun Fragment.checkPassword(text1: Editable?, text2: Editable?) =
    if (text1.toString() == text2.toString()) {
        passwordValid(text1)
    } else "password not to match"