package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetHomeCountryUseCaseTest {

    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: GetHomeCountryUseCase

    @Before
    fun setup() {
        useCase = GetHomeCountryUseCase(whitelistRepository)
    }

    @Test
    fun `invoke returns home country when set`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        every { whitelistRepository.homeCountry } returns flowOf(home)

        val result = useCase().first()

        assertEquals(home, result)
    }

    @Test
    fun `invoke returns null when no home country set`() = runTest {
        every { whitelistRepository.homeCountry } returns flowOf(null)

        val result = useCase().first()

        assertNull(result)
    }
}
