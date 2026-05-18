package com.roamguard.domain.usecase

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckNetworkAllowedUseCaseTest {

    private val networkRepository: NetworkRepository = mockk()
    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: CheckNetworkAllowedUseCase

    @Before
    fun setup() {
        useCase = CheckNetworkAllowedUseCase(networkRepository, whitelistRepository)
    }

    @Test
    fun `returns true when network is home country`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        coEvery { whitelistRepository.homeCountry } returns flowOf(home)
        val network = NetworkInfo(262, 1, "Germany", "Test", "26201", isRoaming = false)

        val result = useCase(network)

        assertTrue(result)
    }

    @Test
    fun `returns true when network is whitelisted`() = runTest {
        coEvery { whitelistRepository.homeCountry } returns flowOf(null)
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", isRoaming = true, isWhitelisted = true)

        val result = useCase(network)

        assertTrue(result)
    }

    @Test
    fun `returns false when network is not home or whitelisted`() = runTest {
        val home = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        coEvery { whitelistRepository.homeCountry } returns flowOf(home)
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", isRoaming = true, isWhitelisted = false)

        val result = useCase(network)

        assertFalse(result)
    }

    @Test
    fun `returns false when home country is null and network is not whitelisted`() = runTest {
        coEvery { whitelistRepository.homeCountry } returns flowOf(null)
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", isRoaming = true, isWhitelisted = false)

        val result = useCase(network)

        assertFalse(result)
    }
}
