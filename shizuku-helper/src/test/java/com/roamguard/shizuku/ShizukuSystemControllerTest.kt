package com.roamguard.shizuku

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ShizukuSystemControllerTest {

    private val context: Context = mockk()
    private val shizukuHelper: ShizukuHelper = mockk()
    private lateinit var controller: ShizukuSystemController

    @Before
    fun setup() {
        controller = ShizukuSystemController(context, shizukuHelper)
    }

    @Test
    fun `forceManualNetworkSelection with plmn delegates to setNetworkSelectionModeManual`() {
        every { shizukuHelper.isAvailable } returns true
        every { shizukuHelper.setNetworkSelectionModeManual("26201") } returns true
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.forceManualNetworkSelection("26201")

        assertTrue(result)
        verify { shizukuHelper.setNetworkSelectionModeManual("26201") }
    }

    @Test
    fun `forceManualNetworkSelection with null delegates to setNetworkSelectionModeAutomatic`() {
        every { shizukuHelper.isAvailable } returns true
        every { shizukuHelper.setNetworkSelectionModeAutomatic() } returns true
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.forceManualNetworkSelection(null)

        assertTrue(result)
        verify { shizukuHelper.setNetworkSelectionModeAutomatic() }
    }

    @Test
    fun `forceManualNetworkSelection returns false when unavailable`() {
        every { shizukuHelper.isAvailable } returns false
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.forceManualNetworkSelection("26201")

        assertFalse(result)
    }

    @Test
    fun `setDataRoaming delegates to shizukuHelper`() {
        every { shizukuHelper.isAvailable } returns true
        every { shizukuHelper.setDataRoamingEnabled(0, true) } returns true
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.setDataRoaming(true)

        assertTrue(result)
        verify { shizukuHelper.setDataRoamingEnabled(0, true) }
    }

    @Test
    fun `setDataRoaming returns false when unavailable`() {
        every { shizukuHelper.isAvailable } returns false
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.setDataRoaming(true)

        assertFalse(result)
    }

    @Test
    fun `getCurrentNetworkCountry delegates to shizukuHelper`() {
        every { shizukuHelper.isAvailable } returns true
        every { shizukuHelper.getCurrentNetworkCountry() } returns "FR"
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.getCurrentNetworkCountry()

        assertEquals("FR", result)
    }

    @Test
    fun `getCurrentNetworkCountry returns null when unavailable`() {
        every { shizukuHelper.isAvailable } returns false
        every { shizukuHelper.init(context) } returns Unit

        val result = controller.getCurrentNetworkCountry()

        assertEquals(null, result)
    }
}
