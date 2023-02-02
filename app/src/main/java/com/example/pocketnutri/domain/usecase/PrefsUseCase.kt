package com.example.pocketnutri.domain.usecase

import com.example.pocketnutri.domain.repository.PrefsRepository
import javax.inject.Inject

class PrefsUseCase @Inject constructor(
    private val repository: PrefsRepository
) {
    fun setPrefBoolean(prefName: String, value: Boolean) = repository.setPrefsBoolean(prefName, value)
    fun getPrefBoolean(prefName: String) = repository.getPrefsBoolean(prefName)
    fun setPrefString(prefName: String, value: String) = repository.setPrefsString(prefName, value)
    fun getPrefString(prefName: String) = repository.getPrefsString(prefName)
    fun deletePref(prefName: String) = repository.deletePrefs(prefName)
}