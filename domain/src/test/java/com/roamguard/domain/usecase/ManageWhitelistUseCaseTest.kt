package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ManageWhitelistUseCaseTest {

    private val whitelistRepository: WhitelistRepository = mockk(relaxed = true)
    private lateinit var useCase: ManageWhitelistUseCase

    @Before
    fun setup() {
        useCase = ManageWhitelistUseCase(whitelistRepository)
    }

    @Test
    fun `home country emits from repository`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        every { whitelistRepository.homeCountry } returns flowOf(home)
        useCase = ManageWhitelistUseCase(whitelistRepository)

        val result = useCase.homeCountry.first()

        assertEquals(home, result)
    }

    @Test
    fun `whitelist emits from repository`() = runTest {
        val entries = listOf(
            WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR"),
            WhitelistCountry(mcc = 214, countryName = "Spain", countryCode = "ES")
        )
        every { whitelistRepository.whitelist } returns flowOf(entries)
        useCase = ManageWhitelistUseCase(whitelistRepository)

        val result = useCase.whitelist.first()

        assertEquals(2, result.size)
        assertEquals("France", result[0].countryName)
    }

    @Test
    fun `setHomeCountry delegates to repository`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        coEvery { whitelistRepository.setHomeCountry(any()) } returns Unit

        useCase.setHomeCountry(home)

        coVerify { whitelistRepository.setHomeCountry(home) }
    }

    @Test
    fun `addToWhitelist delegates to repository`() = runTest {
        val entry = WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR")
        coEvery { whitelistRepository.addToWhitelist(any()) } returns Unit

        useCase.addToWhitelist(entry)

        coVerify { whitelistRepository.addToWhitelist(entry) }
    }

    @Test
    fun `removeFromWhitelist delegates to repository`() = runTest {
        coEvery { whitelistRepository.removeFromWhitelist(any()) } returns Unit

        useCase.removeFromWhitelist(208)

        coVerify { whitelistRepository.removeFromWhitelist(208) }
    }

    @Test
    fun `isWhitelisted returns true when mcc is whitelisted`() = runTest {
        coEvery { whitelistRepository.isWhitelisted(any()) } returns true

        val result = useCase.isWhitelisted(208)

        assertTrue(result)
    }

    @Test
    fun `isWhitelisted returns false when mcc is not whitelisted`() = runTest {
        coEvery { whitelistRepository.isWhitelisted(any()) } returns false

        val result = useCase.isWhitelisted(999)

        assertFalse(result)
    }
}
