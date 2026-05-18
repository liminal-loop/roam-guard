package com.roamguard.data.repository

import com.roamguard.data.local.datastore.PreferencesDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SettingsRepositoryImplTest {

    private val dataStore: PreferencesDataStore = mockk()
    private lateinit var repository: SettingsRepositoryImpl

    @Before
    fun setup() {
        repository = SettingsRepositoryImpl(dataStore)
    }

    @Test
    fun `isOnboardingComplete delegates to dataStore`() = runTest {
        every { dataStore.isOnboardingComplete } returns flowOf(true)

        val result = repository.isOnboardingComplete.first()

        assertTrue(result)
    }

    @Test
    fun `isServiceEnabled delegates to dataStore`() = runTest {
        every { dataStore.isServiceEnabled } returns flowOf(false)

        val result = repository.isServiceEnabled.first()

        assertFalse(result)
    }

    @Test
    fun `scanIntervalSeconds delegates to dataStore`() = runTest {
        every { dataStore.scanInterval } returns flowOf(45)

        val result = repository.scanIntervalSeconds.first()

        assertEquals(45, result)
    }

    @Test
    fun `setOnboardingComplete delegates to dataStore`() = runTest {
        coEvery { dataStore.setOnboardingComplete(any()) } returns Unit

        repository.setOnboardingComplete(true)

        coVerify { dataStore.setOnboardingComplete(true) }
    }

    @Test
    fun `setServiceEnabled delegates to dataStore`() = runTest {
        coEvery { dataStore.setServiceEnabled(any()) } returns Unit

        repository.setServiceEnabled(true)

        coVerify { dataStore.setServiceEnabled(true) }
    }

    @Test
    fun `setScanInterval delegates to dataStore`() = runTest {
        coEvery { dataStore.setScanInterval(any()) } returns Unit

        repository.setScanInterval(30)

        coVerify { dataStore.setScanInterval(30) }
    }
}
