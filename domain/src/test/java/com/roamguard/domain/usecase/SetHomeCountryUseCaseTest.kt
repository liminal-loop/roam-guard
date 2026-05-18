package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetHomeCountryUseCaseTest {

    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: SetHomeCountryUseCase

    @Before
    fun setup() {
        useCase = SetHomeCountryUseCase(whitelistRepository)
    }

    @Test
    fun `invoke sets home country via repository`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        coEvery { whitelistRepository.setHomeCountry(any()) } returns Unit

        useCase(home)

        coVerify { whitelistRepository.setHomeCountry(home) }
    }
}
