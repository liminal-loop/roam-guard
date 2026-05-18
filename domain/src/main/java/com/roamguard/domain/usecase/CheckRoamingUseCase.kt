package com.roamguard.domain.usecase

import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import javax.inject.Inject

class CheckRoamingUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val whitelistRepository: WhitelistRepository
) {
    suspend operator fun invoke(): RoamingDecision {
        return networkRepository.evaluateRoaming()
    }
}
