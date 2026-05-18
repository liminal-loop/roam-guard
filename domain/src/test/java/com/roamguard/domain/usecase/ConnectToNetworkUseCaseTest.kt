package com.roamguard.domain.usecase

import com.roamguard.domain.repository.NetworkRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConnectToNetworkUseCaseTest {

    private val networkRepository: NetworkRepository = mockk()
    private lateinit var useCase: ConnectToNetworkUseCase

    @Before
    fun setup() {
        useCase = ConnectToNetworkUseCase(networkRepository)
    }

    @Test
    fun `invoke returns true when connection succeeds`() = runTest {
        coEvery { networkRepository.connectToNetwork(any()) } returns true

        val result = useCase("26201")

        assertTrue(result)
    }

    @Test
    fun `invoke returns false when connection fails`() = runTest {
        coEvery { networkRepository.connectToNetwork(any()) } returns false

        val result = useCase("20815")

        assertFalse(result)
    }
}
