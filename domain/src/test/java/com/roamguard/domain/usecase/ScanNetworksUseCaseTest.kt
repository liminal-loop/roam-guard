package com.roamguard.domain.usecase

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.repository.NetworkRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ScanNetworksUseCaseTest {

    private val networkRepository: NetworkRepository = mockk()
    private lateinit var useCase: ScanNetworksUseCase

    @Before
    fun setup() {
        useCase = ScanNetworksUseCase(networkRepository)
    }

    @Test
    fun `invoke returns scanned networks`() = runTest {
        val networks = listOf(
            NetworkInfo(262, 1, "Germany", "Deutsche Telekom", "26201", isRoaming = false),
            NetworkInfo(208, 15, "France", "Orange F", "20815", isRoaming = true)
        )
        coEvery { networkRepository.scanAvailableNetworks() } returns networks

        val result = useCase()

        assertEquals(2, result.size)
        assertEquals("Deutsche Telekom", result[0].operatorName)
    }

    @Test
    fun `invoke returns empty list when no networks found`() = runTest {
        coEvery { networkRepository.scanAvailableNetworks() } returns emptyList()

        val result = useCase()

        assertTrue(result.isEmpty())
    }
}
