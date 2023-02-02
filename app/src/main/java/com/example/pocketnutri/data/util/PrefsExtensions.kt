@file:Suppress("UNCHECKED_CAST")

package com.example.pocketnutri.data.util

import android.content.SharedPreferences

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T)
: T {
    when (T::class) {
        Boolean::class -> return getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return getFloat(key, defaultValue as Float) as T
        String::class -> return getString(key, defaultValue as String) as T
        Int::class -> return getInt(key,defaultValue as Int) as T
        Long::class -> return getLong(key, defaultValue as Long) as T
        else -> if (defaultValue is Set<*>) return getStringSet(key, defaultValue as Set<String>) as T
    }
    return defaultValue
}

inline fun <reified T> SharedPreferences.put(sharedName: String, value:T){
    val editor = edit()
    when (T::class) {
        Boolean::class -> editor.putBoolean(sharedName, value as Boolean)
        Float::class -> editor.putFloat(sharedName, value as Float)
        String::class -> editor.putString(sharedName, value as String)
        Int::class -> editor.putInt(sharedName, value as Int)
        Long::class -> editor.putLong(sharedName, value as Long)
        else -> if (value is Set<*>) editor.putStringSet(sharedName, value as Set<String>)
    }
    editor.apply()
}

fun SharedPreferences.remove(key:String) = edit().remove(key).apply()