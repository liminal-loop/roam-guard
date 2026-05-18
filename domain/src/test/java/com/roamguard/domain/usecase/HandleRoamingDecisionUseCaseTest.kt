package com.roamguard.domain.usecase

import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HandleRoamingDecisionUseCaseTest {

    private val networkRepository: NetworkRepository = mockk()
    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var useCase: HandleRoamingDecisionUseCase

    @Before
    fun setup() {
        useCase = HandleRoamingDecisionUseCase(networkRepository, whitelistRepository)
    }

    @Test
    fun `invoke returns Allowed when on home network`() = runTest {
        coEvery { networkRepository.evaluateRoaming() } returns RoamingDecision.Allowed

        val result = useCase()

        assertTrue(result is HandleRoamingResult.Allowed)
    }

    @Test
    fun `invoke returns Denied when no network`() = runTest {
        coEvery { networkRepository.evaluateRoaming() } returns RoamingDecision.Denied("No network")

        val result = useCase()

        assertTrue(result is HandleRoamingResult.Denied)
    }

    @Test
    fun `invoke returns NeedsConfirmation when roaming`() = runTest {
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", true)
        coEvery { networkRepository.evaluateRoaming() } returns
                RoamingDecision.NeedsConfirmation(network, "France")

        val result = useCase()

        assertTrue(result is HandleRoamingResult.NeedsConfirmation)
        val needsConf = result as HandleRoamingResult.NeedsConfirmation
        assertEquals("France", needsConf.decision.country)
    }

    @Test
    fun `allowNetwork with alwaysAllow adds to whitelist`() = runTest {
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", true)
        val decision = RoamingDecision.NeedsConfirmation(network, "France")
        coEvery { whitelistRepository.addToWhitelist(any()) } returns Unit

        useCase.allowNetwork(decision, alwaysAllow = true)

        coVerify { whitelistRepository.addToWhitelist(any()) }
    }

    @Test
    fun `allowNetwork without alwaysAllow does not add to whitelist`() = runTest {
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", true)
        val decision = RoamingDecision.NeedsConfirmation(network, "France")

        useCase.allowNetwork(decision, alwaysAllow = false)

        coVerify(exactly = 0) { whitelistRepository.addToWhitelist(any()) }
    }

    @Test
    fun `denyNetwork triggers recheck and reconnect`() = runTest {
        coEvery { networkRepository.recheckAndReconnect() } returns true

        useCase.denyNetwork()

        coVerify { networkRepository.recheckAndReconnect() }
    }
}
