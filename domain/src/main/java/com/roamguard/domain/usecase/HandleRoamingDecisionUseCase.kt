package com.roamguard.domain.usecase

import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import javax.inject.Inject

sealed class HandleRoamingResult {
    data object Allowed : HandleRoamingResult()
    data object Denied : HandleRoamingResult()
    data class NeedsConfirmation(val decision: RoamingDecision.NeedsConfirmation) : HandleRoamingResult()
    data class Error(val message: String) : HandleRoamingResult()
}

class HandleRoamingDecisionUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val whitelistRepository: WhitelistRepository
) {
    suspend operator fun invoke(): HandleRoamingResult {
        return when (val decision = networkRepository.evaluateRoaming()) {
            is RoamingDecision.Allowed -> HandleRoamingResult.Allowed
            is RoamingDecision.Denied -> HandleRoamingResult.Denied
            is RoamingDecision.NeedsConfirmation -> HandleRoamingResult.NeedsConfirmation(decision)
        }
    }

    suspend fun allowNetwork(decision: RoamingDecision.NeedsConfirmation, alwaysAllow: Boolean) {
        if (alwaysAllow) {
            whitelistRepository.addToWhitelist(
                WhitelistCountry(
                    mcc = decision.network.mcc,
                    countryName = decision.country,
                    countryCode = ""
                )
            )
        }
    }

    suspend fun denyNetwork() {
        networkRepository.recheckAndReconnect()
    }
}
