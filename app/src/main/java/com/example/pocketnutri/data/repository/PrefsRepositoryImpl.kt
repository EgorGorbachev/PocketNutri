package com.example.pocketnutri.data.repository

import com.example.pocketnutri.data.source.local.data_sources.PrefsDataSource
import com.example.pocketnutri.domain.repository.PrefsRepository
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val data: PrefsDataSource
) : PrefsRepository {
    override fun setPrefsBoolean(prefName: String, value: Boolean) = data.setPrefBoolean(prefName, value)
    override fun getPrefsBoolean(prefName: String): Boolean = data.getPrefBoolean(prefName)
    override fun setPrefsString(prefName: String, value: String) = data.setPrefString(prefName,value)
    override fun getPrefsString(prefName: String): String = data.getPrefString(prefName)
    override fun deletePrefs(prefName: String) = data.delete(prefName)
}