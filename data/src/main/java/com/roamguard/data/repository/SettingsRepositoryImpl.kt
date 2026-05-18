package com.roamguard.data.repository

import com.roamguard.data.local.datastore.PreferencesDataStore
import com.roamguard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: PreferencesDataStore
) : SettingsRepository {

    override val isOnboardingComplete: Flow<Boolean> = dataStore.isOnboardingComplete
    override val isServiceEnabled: Flow<Boolean> = dataStore.isServiceEnabled
    override val scanIntervalSeconds: Flow<Int> = dataStore.scanInterval

    override suspend fun setOnboardingComplete(complete: Boolean) {
        dataStore.setOnboardingComplete(complete)
    }

    override suspend fun setServiceEnabled(enabled: Boolean) {
        dataStore.setServiceEnabled(enabled)
    }

    override suspend fun setScanInterval(seconds: Int) {
        dataStore.setScanInterval(seconds)
    }
}
