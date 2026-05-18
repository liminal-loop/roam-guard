package com.roamguard.domain.usecase

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.repository.NetworkRepository
import javax.inject.Inject

class ScanNetworksUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(): List<NetworkInfo> {
        return networkRepository.scanAvailableNetworks()
    }
}
