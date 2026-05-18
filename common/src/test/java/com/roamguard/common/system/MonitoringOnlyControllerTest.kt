package com.roamguard.common.system

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class MonitoringOnlyControllerTest {

    private lateinit var controller: MonitoringOnlyController

    @Before
    fun setup() {
        controller = MonitoringOnlyController()
    }

    @Test
    fun `forceManualNetworkSelection returns false`() {
        assertFalse(controller.forceManualNetworkSelection("26201"))
        assertFalse(controller.forceManualNetworkSelection(null))
    }

    @Test
    fun `setDataRoaming returns false`() {
        assertFalse(controller.setDataRoaming(true))
        assertFalse(controller.setDataRoaming(false))
    }

    @Test
    fun `getCurrentNetworkCountry returns null`() {
        assertNull(controller.getCurrentNetworkCountry())
    }
}
