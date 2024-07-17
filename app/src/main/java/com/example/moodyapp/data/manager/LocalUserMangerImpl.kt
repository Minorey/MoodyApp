package com.example.moodyapp.data.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserMangerImpl @Inject constructor(private val context: DataStore<Preferences>) {

    suspend fun updatePreference(key: Preferences.Key<Boolean>, value: Boolean=false) {
        context.edit { preferences ->
            preferences[key] = value}
    }

    fun getPreferenceFlow(key: Preferences.Key<Boolean>, defaultValue: Boolean): Flow<Boolean> {
        return context.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    fun getPreferenceStringFlow(key: Preferences.Key<String>, defaultValue: String): Flow<String> {
        return context.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun updatePreferenceString(key: Preferences.Key<String>, value: String="") {
        context.edit { preferences ->
            preferences[key] = value}
    }

    companion object {
        val ONBOARDING_CHECKED_KEY = booleanPreferencesKey("ONBOARDING_CHECKED_KEY")
        val CONTENT_DIARY_= stringPreferencesKey("CONTENT_DIARY")
        val TITLE_DIARY=stringPreferencesKey("TITLE_DIARY")
    }
}