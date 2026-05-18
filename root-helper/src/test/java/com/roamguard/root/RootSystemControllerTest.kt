package com.roamguard.root

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RootSystemControllerTest {

    private val rootHelper: RootHelper = mockk()
    private lateinit var controller: RootSystemController

    @Before
    fun setup() {
        controller = RootSystemController(rootHelper)
    }

    @Test
    fun `forceManualNetworkSelection with plmn delegates to setManualNetworkSelection`() {
        every { rootHelper.isAvailable } returns true
        every { rootHelper.setManualNetworkSelection("26201") } returns true

        val result = controller.forceManualNetworkSelection("26201")

        assertTrue(result)
        verify { rootHelper.setManualNetworkSelection("26201") }
    }

    @Test
    fun `forceManualNetworkSelection with null delegates to setAutomaticNetworkSelection`() {
        every { rootHelper.isAvailable } returns true
        every { rootHelper.setAutomaticNetworkSelection() } returns true

        val result = controller.forceManualNetworkSelection(null)

        assertTrue(result)
        verify { rootHelper.setAutomaticNetworkSelection() }
    }

    @Test
    fun `forceManualNetworkSelection returns false when root unavailable`() {
        every { rootHelper.isAvailable } returns false

        val result = controller.forceManualNetworkSelection("26201")

        assertFalse(result)
    }

    @Test
    fun `setDataRoaming delegates to rootHelper`() {
        every { rootHelper.isAvailable } returns true
        every { rootHelper.setDataRoaming(true) } returns true

        val result = controller.setDataRoaming(true)

        assertTrue(result)
        verify { rootHelper.setDataRoaming(true) }
    }

    @Test
    fun `setDataRoaming returns false when root unavailable`() {
        every { rootHelper.isAvailable } returns false

        val result = controller.setDataRoaming(true)

        assertFalse(result)
    }

    @Test
    fun `getCurrentNetworkCountry delegates to rootHelper`() {
        every { rootHelper.isAvailable } returns true
        every { rootHelper.getCurrentNetworkCountry() } returns "DE"

        val result = controller.getCurrentNetworkCountry()

        assertEquals("DE", result)
    }

    @Test
    fun `getCurrentNetworkCountry returns null when root unavailable`() {
        every { rootHelper.isAvailable } returns false

        val result = controller.getCurrentNetworkCountry()

        assertEquals(null, result)
    }
}
