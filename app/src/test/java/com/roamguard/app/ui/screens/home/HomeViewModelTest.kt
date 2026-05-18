package com.roamguard.app.ui.screens.home

import android.app.Application
import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import com.roamguard.domain.usecase.CheckRoamingUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private val application: Application = mockk()
    private val checkRoamingUseCase: CheckRoamingUseCase = mockk()
    private val networkRepository: NetworkRepository = mockk()
    private val whitelistRepository: WhitelistRepository = mockk()
    private val settingsRepository: SettingsRepository = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        every { whitelistRepository.homeCountry } returns flowOf(
            HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        )
        every { settingsRepository.isServiceEnabled } returns flowOf(false)
        every { settingsRepository.isOnboardingComplete } returns flowOf(true)
        coEvery { settingsRepository.setServiceEnabled(any()) } returns Unit
        viewModel = HomeViewModel(
            application, checkRoamingUseCase, networkRepository,
            whitelistRepository, settingsRepository
        )
    }

    @Test
    fun `homeCountry loads from repository`() {
        val home = viewModel.homeCountry.value

        assertNotNull(home)
        assertEquals("Germany", home?.countryName)
    }

    @Test
    fun `isServiceEnabled defaults to false`() {
        assertFalse(viewModel.isServiceEnabled.value)
    }

    @Test
    fun `checkRoaming shows dialog when needs confirmation`() = runTest {
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", isRoaming = true)
        coEvery { checkRoamingUseCase() } returns RoamingDecision.NeedsConfirmation(network, "France")

        viewModel.checkRoaming()

        assertTrue(viewModel.showDialog.value)
        assertEquals("France", viewModel.dialogCountry.value)
        assertEquals(208, viewModel.dialogNetworkMcc.value)
    }

    @Test
    fun `checkRoaming does not show dialog when allowed`() = runTest {
        coEvery { checkRoamingUseCase() } returns RoamingDecision.Allowed

        viewModel.checkRoaming()

        assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `checkRoaming does not show dialog when denied`() = runTest {
        coEvery { checkRoamingUseCase() } returns RoamingDecision.Denied("No network")

        viewModel.checkRoaming()

        assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `allowNetwork without always allow hides dialog`() {
        viewModel.allowNetwork(false, null)

        assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `denyNetwork hides dialog`() = runTest {
        coEvery { networkRepository.recheckAndReconnect() } returns true

        viewModel.denyNetwork()

        assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `dismissDialog hides dialog`() {
        viewModel.dismissDialog()

        assertFalse(viewModel.showDialog.value)
    }
}
