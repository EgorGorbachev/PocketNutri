package com.example.pocketnutri.ui.util.validation


private fun String.matchTo(regExp: String) = matches(regExp.toRegex())

fun String.isEmailValid() = this.matchTo(EMAIL)

fun String.isPasswordValid() = this.matchTo(PASSWORD)