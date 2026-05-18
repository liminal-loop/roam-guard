package com.roamguard.domain.usecase

import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.WhitelistRepository
import javax.inject.Inject

class UpdateWhitelistUseCase @Inject constructor(
    private val whitelistRepository: WhitelistRepository
) {
    suspend operator fun invoke(entry: WhitelistCountry) {
        whitelistRepository.addToWhitelist(entry)
    }

    suspend fun remove(mcc: Int) {
        whitelistRepository.removeFromWhitelist(mcc)
    }

    suspend fun isWhitelisted(mcc: Int): Boolean {
        return whitelistRepository.isWhitelisted(mcc)
    }
}
