package com.roamguard.domain.usecase

import com.roamguard.domain.repository.NetworkRepository
import javax.inject.Inject

class ConnectToNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(plmn: String): Boolean {
        return networkRepository.connectToNetwork(plmn)
    }
}
