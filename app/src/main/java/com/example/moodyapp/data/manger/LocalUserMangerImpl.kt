package com.example.moodyapp.data.manger

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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

    companion object {
        val ONBOARDING_CHECKED_KEY = booleanPreferencesKey("ONBOARDING_CHECKED_KEY")
    }
}