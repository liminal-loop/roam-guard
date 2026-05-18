package com.roamguard.domain.repository

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import kotlinx.coroutines.flow.Flow

interface WhitelistRepository {
    val homeCountry: Flow<HomeCountry?>
    val whitelist: Flow<List<WhitelistCountry>>

    suspend fun setHomeCountry(country: HomeCountry)
    suspend fun addToWhitelist(country: WhitelistCountry)
    suspend fun removeFromWhitelist(mcc: Int)
    suspend fun isWhitelisted(mcc: Int): Boolean
}
