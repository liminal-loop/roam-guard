package com.roamguard.app.ui.screens.onboarding

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OnboardingViewModelTest {

    private val settingsRepository: SettingsRepository = mockk()
    private val whitelistRepository: WhitelistRepository = mockk()
    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setup() {
        every { settingsRepository.isOnboardingComplete } returns flowOf(null)
        every { whitelistRepository.homeCountry } returns flowOf(null)
        every { whitelistRepository.whitelist } returns flowOf(emptyList())
        coEvery { settingsRepository.setOnboardingComplete(any()) } returns Unit
        coEvery { whitelistRepository.setHomeCountry(any()) } returns Unit
        viewModel = OnboardingViewModel(settingsRepository, whitelistRepository)
    }

    @Test
    fun `countries list is populated`() {
        assertFalse(viewModel.countries.isEmpty())
        assertTrue(viewModel.countries.any { it.countryName == "Germany" })
    }

    @Test
    fun `selectCountry sets selected mcc`() {
        viewModel.selectCountry(262)

        assertEquals(262, viewModel.selectedMcc.value)
    }

    @Test
    fun `updateSearchQuery updates search text`() {
        viewModel.updateSearchQuery("Germany")

        assertEquals("Germany", viewModel.searchQuery.value)
    }

    @Test
    fun `completeOnboarding sets home country and marks complete`() = runTest {
        viewModel.selectCountry(262)
        viewModel.completeOnboarding()

        coVerify { whitelistRepository.setHomeCountry(any()) }
        coVerify { settingsRepository.setOnboardingComplete(true) }
        assertTrue(viewModel.isComplete.value)
    }

    @Test
    fun `completeOnboarding does nothing without selected country`() = runTest {
        viewModel.completeOnboarding()

        coVerify(exactly = 0) { whitelistRepository.setHomeCountry(any()) }
        assertFalse(viewModel.isComplete.value)
    }

    @Test
    fun `skipOnboarding marks complete without setting home country`() = runTest {
        viewModel.skipOnboarding()

        coVerify(exactly = 0) { whitelistRepository.setHomeCountry(any()) }
        coVerify { settingsRepository.setOnboardingComplete(true) }
        assertTrue(viewModel.isComplete.value)
    }
}
