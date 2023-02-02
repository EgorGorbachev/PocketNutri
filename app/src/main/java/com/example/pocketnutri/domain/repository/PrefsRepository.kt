package com.example.pocketnutri.domain.repository

interface PrefsRepository {
    fun setPrefsBoolean(prefName:String, value:Boolean)
    fun getPrefsBoolean(prefName:String):Boolean
    fun setPrefsString(prefName:String, value:String)
    fun getPrefsString(prefName:String):String
    fun deletePrefs(prefName: String)
}