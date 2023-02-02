package com.example.pocketnutri.ui.util.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes messageStringRes: Int) {

    val toast = Toast.makeText(this, messageStringRes, Toast.LENGTH_SHORT)
    toast.show()
}