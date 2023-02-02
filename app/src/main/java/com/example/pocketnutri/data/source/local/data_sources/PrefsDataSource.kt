package com.example.pocketnutri.data.source.local.data_sources

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.pocketnutri.data.constants.CALORIES
import com.example.pocketnutri.data.constants.KEY_SHARED_PREFS_NAME
import com.example.pocketnutri.data.constants.REMEMBER_USER
import com.example.pocketnutri.data.util.get
import com.example.pocketnutri.data.util.put
import com.example.pocketnutri.data.util.remove
import javax.inject.Inject

class PrefsDataSource @Inject constructor(
    private val application: Application
) {
    private val prefs by lazy {
        val masterKey = MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            application,
            KEY_SHARED_PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setPrefBoolean(key:String, value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }

    fun getPrefBoolean(key:String): Boolean {
        return prefs.getBoolean(key,false)
    }

    fun setPrefString(key:String, value: String) {
        val editor = prefs.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun getPrefString(key:String): String {
        return prefs.getString(key,"")!!
    }
    fun delete(prefName:String) = prefs.remove(prefName)
}