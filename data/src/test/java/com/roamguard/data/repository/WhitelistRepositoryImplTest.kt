package com.roamguard.data.repository

import com.roamguard.data.local.dao.HomeCountryDao
import com.roamguard.data.local.dao.WhitelistDao
import com.roamguard.data.local.entity.HomeCountryEntity
import com.roamguard.data.local.entity.WhitelistCountryEntity
import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.WhitelistCountry
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WhitelistRepositoryImplTest {

    private val homeCountryDao: HomeCountryDao = mockk(relaxed = true)
    private val whitelistDao: WhitelistDao = mockk(relaxed = true)
    private lateinit var repository: WhitelistRepositoryImpl

    @Before
    fun setup() {
        repository = WhitelistRepositoryImpl(homeCountryDao, whitelistDao)
    }

    @Test
    fun `homeCountry maps entity to domain model`() = runTest {
        val entity = HomeCountryEntity(id = 1L, mcc = 262, countryName = "Germany", countryCode = "DE")
        every { homeCountryDao.getHomeCountry() } returns flowOf(entity)
        repository = WhitelistRepositoryImpl(homeCountryDao, whitelistDao)

        val result = repository.homeCountry.first()

        assertEquals(1L, result?.id)
        assertEquals(262, result?.mcc)
        assertEquals("Germany", result?.countryName)
        assertEquals("DE", result?.countryCode)
    }

    @Test
    fun `homeCountry returns null when no entity`() = runTest {
        every { homeCountryDao.getHomeCountry() } returns flowOf(null)
        repository = WhitelistRepositoryImpl(homeCountryDao, whitelistDao)

        val result = repository.homeCountry.first()

        assertNull(result)
    }

    @Test
    fun `whitelist maps entities to domain models`() = runTest {
        val entities = listOf(
            WhitelistCountryEntity(id = 1L, mcc = 208, countryName = "France", countryCode = "FR"),
            WhitelistCountryEntity(id = 2L, mcc = 214, countryName = "Spain", countryCode = "ES")
        )
        every { whitelistDao.getAll() } returns flowOf(entities)
        repository = WhitelistRepositoryImpl(homeCountryDao, whitelistDao)

        val result = repository.whitelist.first()

        assertEquals(2, result.size)
        assertEquals("France", result[0].countryName)
        assertEquals("ES", result[1].countryCode)
    }

    @Test
    fun `setHomeCountry converts and delegates to DAO`() = runTest {
        val country = HomeCountry(mcc = 262, countryName = "Germany", countryCode = "DE")

        repository.setHomeCountry(country)

        coVerify {
            homeCountryDao.setHomeCountry(
                HomeCountryEntity(mcc = 262, countryName = "Germany", countryCode = "DE")
            )
        }
    }

    @Test
    fun `addToWhitelist converts and delegates to DAO`() = runTest {
        val country = WhitelistCountry(mcc = 208, countryName = "France", countryCode = "FR")

        repository.addToWhitelist(country)

        coVerify {
            whitelistDao.insert(
                WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR")
            )
        }
    }

    @Test
    fun `removeFromWhitelist delegates to DAO`() = runTest {
        repository.removeFromWhitelist(208)

        coVerify { whitelistDao.deleteByMcc(208) }
    }

    @Test
    fun `isWhitelisted returns true when exists`() = runTest {
        coEvery { whitelistDao.exists(208) } returns 1

        val result = repository.isWhitelisted(208)

        assertTrue(result)
    }

    @Test
    fun `isWhitelisted returns false when not exists`() = runTest {
        coEvery { whitelistDao.exists(999) } returns 0

        val result = repository.isWhitelisted(999)

        assertTrue(!result)
    }
}
