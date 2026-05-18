package com.roamguard.domain.repository

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    val currentNetwork: Flow<NetworkInfo?>
    val availableNetworks: Flow<List<NetworkInfo>>

    suspend fun getCurrentNetwork(): NetworkInfo?
    suspend fun scanAvailableNetworks(): List<NetworkInfo>
    suspend fun connectToNetwork(plmn: String): Boolean
    suspend fun setDataRoamingEnabled(enabled: Boolean): Boolean
    suspend fun evaluateRoaming(): RoamingDecision
    suspend fun recheckAndReconnect(): Boolean
}
