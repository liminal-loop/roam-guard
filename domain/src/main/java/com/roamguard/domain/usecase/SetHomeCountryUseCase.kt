package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.WhitelistRepository
import javax.inject.Inject

class SetHomeCountryUseCase @Inject constructor(
    private val whitelistRepository: WhitelistRepository
) {
    suspend operator fun invoke(country: HomeCountry) {
        whitelistRepository.setHomeCountry(country)
    }
}
