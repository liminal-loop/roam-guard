package com.roamguard.data.local.dao

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import com.roamguard.data.local.db.RoamGuardDatabase
import com.roamguard.data.local.entity.HomeCountryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class HomeCountryDaoTest {

    private lateinit var database: RoamGuardDatabase
    private lateinit var dao: HomeCountryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            RoamGuardDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.homeCountryDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `getHomeCountry returns null when empty`() = runTest {
        val result = dao.getHomeCountry().first()

        assertNull(result)
    }

    @Test
    fun `setHomeCountry inserts and retrieves`() = runTest {
        val entity = HomeCountryEntity(mcc = 262, countryName = "Germany", countryCode = "DE")
        dao.setHomeCountry(entity)

        val result = dao.getHomeCountry().first()

        assertEquals(262, result?.mcc)
        assertEquals("Germany", result?.countryName)
    }

    @Test
    fun `setHomeCountry replaces existing entry`() = runTest {
        dao.setHomeCountry(HomeCountryEntity(mcc = 262, countryName = "Germany", countryCode = "DE"))
        dao.setHomeCountry(HomeCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"))

        val result = dao.getHomeCountry().first()

        assertEquals(208, result?.mcc)
        assertEquals("France", result?.countryName)
    }

    @Test
    fun `clearHomeCountry removes home country`() = runTest {
        dao.setHomeCountry(HomeCountryEntity(mcc = 262, countryName = "Germany", countryCode = "DE"))
        dao.clearHomeCountry()

        val result = dao.getHomeCountry().first()

        assertNull(result)
    }
}
