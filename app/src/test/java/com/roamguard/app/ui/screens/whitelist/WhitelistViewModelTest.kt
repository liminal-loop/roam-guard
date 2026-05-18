package com.roamguard.app.ui.screens.whitelist

import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.usecase.ManageWhitelistUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WhitelistViewModelTest {

    private val manageWhitelistUseCase: ManageWhitelistUseCase = mockk()
    private lateinit var viewModel: WhitelistViewModel

    @Before
    fun setup() {
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

    @Test
    fun `homeCountry loads from use case`() {
        val home = viewModel.homeCountry.value

        assertNotNull(home)
        assertEquals("Germany", home?.countryName)
    }

    @Test
    fun `whitelist loads from use case`() {
        val list = viewModel.whitelist.value

        assertEquals(2, list.size)
        assertEquals("France", list[0].countryName)
    }

    @Test
    fun `removeFromWhitelist delegates to use case`() = runTest {
        viewModel.removeFromWhitelist(208)

        coVerify { manageWhitelistUseCase.removeFromWhitelist(208) }
    }
}
