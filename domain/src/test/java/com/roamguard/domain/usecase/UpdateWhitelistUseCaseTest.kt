package com.roamguard.domain.usecase

import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateWhitelistUseCaseTest {

    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: UpdateWhitelistUseCase

    @Before
    fun setup() {
        useCase = UpdateWhitelistUseCase(whitelistRepository)
    }

    @Test
    fun `invoke adds entry to whitelist`() = runTest {
        val entry = WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR")
        coEvery { whitelistRepository.addToWhitelist(any()) } returns Unit

        useCase(entry)

        coVerify { whitelistRepository.addToWhitelist(entry) }
    }

    @Test
    fun `remove deletes entry from whitelist`() = runTest {
        coEvery { whitelistRepository.removeFromWhitelist(any()) } returns Unit

        useCase.remove(208)

        coVerify { whitelistRepository.removeFromWhitelist(208) }
    }

    @Test
    fun `isWhitelisted returns true when mcc exists`() = runTest {
        coEvery { whitelistRepository.isWhitelisted(208) } returns true

        val result = useCase.isWhitelisted(208)

        assertTrue(result)
    }

    @Test
    fun `isWhitelisted returns false when mcc not found`() = runTest {
        coEvery { whitelistRepository.isWhitelisted(999) } returns false

        val result = useCase.isWhitelisted(999)

        assertFalse(result)
    }
}
