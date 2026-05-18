package com.roamguard.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.roamguard.common.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DATASTORE_NAME
)

@Singleton
class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val SERVICE_ENABLED = booleanPreferencesKey("service_enabled")
        val SCAN_INTERVAL = intPreferencesKey("scan_interval")
        val PROMPTED_MCCS = stringPreferencesKey("prompted_mccs")
    }

    val isOnboardingComplete: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.ONBOARDING_COMPLETE] ?: false
    }

    val isServiceEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.SERVICE_ENABLED] ?: false
    }

    val scanInterval: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.SCAN_INTERVAL] ?: Constants.DEFAULT_SCAN_INTERVAL
    }

    suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ONBOARDING_COMPLETE] = complete
        }
    }

    suspend fun setServiceEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SERVICE_ENABLED] = enabled
        }
    }

    suspend fun setScanInterval(seconds: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SCAN_INTERVAL] = seconds.coerceIn(
                Constants.MIN_SCAN_INTERVAL,
                Constants.MAX_SCAN_INTERVAL
            )
        }
    }

    val promptedMccs: Flow<Set<Int>> = context.dataStore.data.map { prefs ->
        prefs[Keys.PROMPTED_MCCS]
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet() ?: emptySet()
    }

    suspend fun markMccPrompted(mcc: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.PROMPTED_MCCS] ?: ""
            val mccs = if (current.isNotEmpty()) current.split(",").toMutableList() else mutableListOf()
            if (!mccs.contains(mcc.toString())) {
                mccs.add(mcc.toString())
                prefs[Keys.PROMPTED_MCCS] = mccs.joinToString(",")
            }
        }
    }

    suspend fun clearPromptedMccs() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.PROMPTED_MCCS)
        }
    }

    suspend fun hasBeenPrompted(mcc: Int): Boolean {
        return promptedMccs.first().contains(mcc)
    }
}
