package com.example.moodyapp.data.manger

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.moodyapp.domain.manger.LocalUserManger
import com.example.moodyapp.util.Constants
import com.example.moodyapp.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserMangerImpl (
    private val context: Context
    ) : LocalUserManger {
        override suspend fun saveAppEntry() {
            context.dataStore.edit { settings ->
                settings[PreferenceKeys.APP_ENTRY] = true
            }
        }

        override fun readAppEntry(): Flow<Boolean> {
            return context.dataStore.data.map { preferences ->
                preferences[PreferenceKeys.APP_ENTRY] ?: false
            }
        }
    }

    private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)

    private val Context.dataStore: DataStore<Preferences> by readOnlyProperty

    private object PreferenceKeys {
        val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
    }
