package com.roamguard.domain.usecase

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CheckRoamingUseCaseTest {

    private val networkRepository: NetworkRepository = mockk()
    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: CheckRoamingUseCase

    @Before
    fun setup() {
        useCase = CheckRoamingUseCase(networkRepository, whitelistRepository)
    }

    @Test
    fun `invoke returns allowed when on home network`() = runTest {
        coEvery { networkRepository.evaluateRoaming() } returns RoamingDecision.Allowed

        val result = useCase()

        assertEquals(RoamingDecision.Allowed, result)
    }

    @Test
    fun `invoke returns denied when no network`() = runTest {
        coEvery { networkRepository.evaluateRoaming() } returns RoamingDecision.Denied("No network")

        val result = useCase()

        assertEquals(RoamingDecision.Denied("No network"), result)
    }

    @Test
    fun `invoke returns needs confirmation when roaming on non-whitelisted network`() = runTest {
        val network = NetworkInfo(262, 1, "Germany", "Test", "26201", true)
        coEvery { networkRepository.evaluateRoaming() } returns
                RoamingDecision.NeedsConfirmation(network, "Germany")

        val result = useCase()

        assert(result is RoamingDecision.NeedsConfirmation)
        assertEquals("Germany", (result as RoamingDecision.NeedsConfirmation).country)
    }
}
