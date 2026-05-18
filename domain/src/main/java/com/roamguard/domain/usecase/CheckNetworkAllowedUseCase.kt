package com.roamguard.domain.usecase

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckNetworkAllowedUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val whitelistRepository: WhitelistRepository
) {
    suspend operator fun invoke(network: NetworkInfo): Boolean {
        val homeCountry = whitelistRepository.homeCountry.first()
        if (homeCountry != null && network.mcc == homeCountry.mcc) return true
        if (network.isWhitelisted) return true
        return false
    }
}
