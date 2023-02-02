package com.example.pocketnutri.ui.util.validation

const val EMAIL =
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"

const val PASSWORD =
    "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"