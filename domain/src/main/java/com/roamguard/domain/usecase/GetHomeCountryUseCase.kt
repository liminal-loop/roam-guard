package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.WhitelistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeCountryUseCase @Inject constructor(
    private val whitelistRepository: WhitelistRepository
) {
    operator fun invoke(): Flow<HomeCountry?> = whitelistRepository.homeCountry
}
