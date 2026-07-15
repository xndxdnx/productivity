package com.example.productivity.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


const val DATASTORE_COLOR = "colors"

private val Context.datastore : DataStore<Preferences> by preferencesDataStore(DATASTORE_COLOR)

class DataStoreManager(
    val context: Context
) {
    suspend fun saveStringPreference(value : String, key: String){
        // edit блокирует хранилище чтобы безопасно изменить настройки
        context.datastore.edit { preference ->
            preference[stringPreferencesKey(key)] = value
        }
    }

    fun getStringPreference (key: String, defValue: String) = context.datastore.data.map { preference ->
        preference[stringPreferencesKey(key)] ?: defValue
    }

    companion object {
        const val TITLE_COLOR = "title_color"
    }

}