package com.roamguard.app.ui.screens.whitelist

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.usecase.ManageWhitelistUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class WhitelistViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val manageWhitelistUseCase: ManageWhitelistUseCase = mockk()
    private lateinit var viewModel: WhitelistViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { manageWhitelistUseCase.homeCountry } returns flowOf(
            HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")
        )
        every { manageWhitelistUseCase.whitelist } returns flowOf(
            listOf(
                WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR"),
                WhitelistCountry(mcc = 214, countryName = "Spain", countryCode = "ES")
            )
        )
        coEvery { manageWhitelistUseCase.removeFromWhitelist(any()) } returns Unit
        viewModel = WhitelistViewModel(manageWhitelistUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `homeCountry loads from use case`() = runTest(testDispatcher) {
        val home = viewModel.homeCountry.first()

        assertNotNull(home)
        assertEquals("Germany", home?.countryName)
    }

    @Test
    fun `whitelist loads from use case`() = runTest(testDispatcher) {
        val list = viewModel.whitelist.first()

        assertEquals(2, list.size)
        assertEquals("France", list[0].countryName)
    }

    @Test
    fun `removeFromWhitelist delegates to use case`() = runTest(testDispatcher) {
        viewModel.removeFromWhitelist(208)

        coVerify { manageWhitelistUseCase.removeFromWhitelist(208) }
    }
}
