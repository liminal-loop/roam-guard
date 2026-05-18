package com.roamguard.data.repository

import com.roamguard.data.local.mcc.MccDatasetLoader
import com.roamguard.mcc.MccEntry
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CountryRepositoryImplTest {

    private val mccDatasetLoader: MccDatasetLoader = mockk()
    private lateinit var repository: CountryRepositoryImpl

    private val testEntries = listOf(
        MccEntry(mcc = 262, countryCode = "DE", countryName = "Germany"),
        MccEntry(mcc = 208, countryCode = "FR", countryName = "France"),
        MccEntry(mcc = 214, countryCode = "ES", countryName = "Spain")
    )

    @Before
    fun setup() {
        repository = CountryRepositoryImpl(mccDatasetLoader)
    }

    @Test
    fun `getAllCountries returns sorted distinct countries`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getAllCountries()

        assertEquals(3, result.size)
        assertEquals("France", result[0].countryName)
        assertEquals("Germany", result[1].countryName)
        assertEquals("Spain", result[2].countryName)
    }

    @Test
    fun `allCountries flow emits loaded countries`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        repository.getAllCountries()
        val result = repository.allCountries.first()

        assertEquals(3, result.size)
    }

    @Test
    fun `getCountryByMcc returns correct country`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryByMcc(208)

        assertNotNull(result)
        assertEquals("France", result?.countryName)
        assertEquals("FR", result?.countryCode)
    }

    @Test
    fun `getCountryByMcc returns null for unknown mcc`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryByMcc(999)

        assertNull(result)
    }

    @Test
    fun `getCountryName returns name for known mcc`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryName(262)

        assertEquals("Germany", result)
    }

    @Test
    fun `getCountryName returns Unknown for unknown mcc`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryName(999)

        assertEquals("Unknown", result)
    }

    @Test
    fun `getCountryCode returns code for known mcc`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryCode(262)

        assertEquals("DE", result)
    }

    @Test
    fun `getCountryCode returns UNKNOWN for unknown mcc`() = runTest {
        every { mccDatasetLoader.getEntries() } returns testEntries

        val result = repository.getCountryCode(999)

        assertEquals("UNKNOWN", result)
    }

    @Test
    fun `getAllCountries deduplicates by mcc`() = runTest {
        val duplicateEntries = testEntries + MccEntry(mcc = 262, countryCode = "DE", countryName = "Germany")
        every { mccDatasetLoader.getEntries() } returns duplicateEntries

        val result = repository.getAllCountries()

        assertEquals(3, result.size)
    }
}
