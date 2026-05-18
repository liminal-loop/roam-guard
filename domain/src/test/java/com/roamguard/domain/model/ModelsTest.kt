package com.roamguard.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import com.roamguard.domain.usecase.HandleRoamingResult
import org.junit.Test

class ModelsTest {

    @Test
    fun `HomeCountry is created with defaults`() {
        val country = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")

        assertEquals(262, country.mcc)
        assertEquals("Germany", country.countryName)
        assertEquals("DE", country.countryCode)
        assertEquals(0L, country.id)
    }

    @Test
    fun `HomeCountry can have custom id`() {
        val country = HomeCountry(id = 5L, mcc = 262, countryName = "Germany", countryCode = "DE")

        assertEquals(5L, country.id)
    }

    @Test
    fun `WhitelistCountry is created with defaults`() {
        val entry = WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR")

        assertEquals(208, entry.mcc)
        assertEquals("France", entry.countryName)
        assertEquals(0L, entry.id)
    }

    @Test
    fun `NetworkInfo is created with correct values`() {
        val info = NetworkInfo(
            mcc = 262, mnc = 1, countryName = "Germany",
            operatorName = "TMobile", plmn = "26201",
            isRoaming = false, isHomeNetwork = true, isWhitelisted = false
        )

        assertEquals("TMobile", info.operatorName)
        assertTrue(info.isHomeNetwork)
    }

    @Test
    fun `RoamingDecision Allowed is a singleton`() {
        assertNotNull(RoamingDecision.Allowed)
    }

    @Test
    fun `RoamingDecision Denied has reason`() {
        val denied = RoamingDecision.Denied("No network")
        assertEquals("No network", denied.reason)
    }

    @Test
    fun `RoamingDecision NeedsConfirmation has network and country`() {
        val network = NetworkInfo(208, 15, "France", "Orange", "20815", true)
        val decision = RoamingDecision.NeedsConfirmation(network, "France")

        assertEquals(network, decision.network)
        assertEquals("France", decision.country)
    }

    @Test
    fun `Country model is created correctly`() {
        val country = Country(mcc = 262, countryName = "Germany", countryCode = "DE")

        assertEquals(262, country.mcc)
        assertEquals("Germany", country.countryName)
        assertEquals("DE", country.countryCode)
    }

    @Test
    fun `WhitelistEntry model is created correctly`() {
        val entry = WhitelistEntry(mcc = 208, countryName = "France", countryCode = "FR")

        assertEquals(208, entry.mcc)
        assertEquals("France", entry.countryName)
        assertEquals(0L, entry.id)
    }

    @Test
    fun `HandleRoamingResult variants exist`() {
        assertTrue(HandleRoamingResult.Allowed is HandleRoamingResult)
        assertTrue(HandleRoamingResult.Denied is HandleRoamingResult)
    }
}
