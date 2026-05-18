package com.roamguard.domain.repository

import com.roamguard.domain.model.HomeCountry
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isOnboardingComplete: Flow<Boolean>
    val isServiceEnabled: Flow<Boolean>
    val scanIntervalSeconds: Flow<Int>

    suspend fun setOnboardingComplete(complete: Boolean)
    suspend fun setServiceEnabled(enabled: Boolean)
    suspend fun setScanInterval(seconds: Int)
}
