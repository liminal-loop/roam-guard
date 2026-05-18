package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.WhitelistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageWhitelistUseCase @Inject constructor(
    private val whitelistRepository: WhitelistRepository
) {
    val homeCountry: Flow<HomeCountry?> = whitelistRepository.homeCountry
    val whitelist: Flow<List<WhitelistCountry>> = whitelistRepository.whitelist

    suspend fun setHomeCountry(country: HomeCountry) {
        whitelistRepository.setHomeCountry(country)
    }

    suspend fun addToWhitelist(country: WhitelistCountry) {
        whitelistRepository.addToWhitelist(country)
    }

    suspend fun removeFromWhitelist(mcc: Int) {
        whitelistRepository.removeFromWhitelist(mcc)
    }

    suspend fun isWhitelisted(mcc: Int): Boolean {
        return whitelistRepository.isWhitelisted(mcc)
    }
}
