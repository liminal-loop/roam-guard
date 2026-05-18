package com.roamguard.data.local.dao

import androidx.room.Room
import com.roamguard.data.local.db.RoamGuardDatabase
import com.roamguard.data.local.entity.WhitelistCountryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class WhitelistDaoTest {

    private lateinit var database: RoamGuardDatabase
    private lateinit var dao: WhitelistDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            RoamGuardDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.whitelistDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `getAll returns empty list initially`() = runTest {
        val result = dao.getAll().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `insert adds country to database`() = runTest {
        val entity = WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR")
        dao.insert(entity)

        val result = dao.getAll().first()

        assertEquals(1, result.size)
        assertEquals("France", result[0].countryName)
    }

    @Test
    fun `insertAll adds multiple countries`() = runTest {
        val entities = listOf(
            WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"),
            WhitelistCountryEntity(mcc = 214, countryName = "Spain", countryCode = "ES")
        )
        dao.insertAll(entities)

        val result = dao.getAll().first()

        assertEquals(2, result.size)
    }

    @Test
    fun `getByMcc returns correct country`() = runTest {
        dao.insert(WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"))

        val result = dao.getByMcc(208)

        assertEquals("France", result?.countryName)
    }

    @Test
    fun `getByMcc returns null for non-existent mcc`() = runTest {
        val result = dao.getByMcc(999)

        assertNull(result)
    }

    @Test
    fun `deleteByMcc removes country`() = runTest {
        dao.insert(WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"))
        dao.deleteByMcc(208)

        val result = dao.getByMcc(208)

        assertNull(result)
    }

    @Test
    fun `delete removes country entity`() = runTest {
        val entity = WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR")
        val id = dao.insert(entity)
        dao.delete(entity.copy(id = id))

        val result = dao.getByMcc(208)

        assertNull(result)
    }

    @Test
    fun `exists returns 1 when mcc present`() = runTest {
        dao.insert(WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"))

        val count = dao.exists(208)

        assertEquals(1, count)
    }

    @Test
    fun `exists returns 0 when mcc not present`() = runTest {
        val count = dao.exists(999)

        assertEquals(0, count)
    }

    @Test
    fun `getAll returns countries sorted by name`() = runTest {
        dao.insert(WhitelistCountryEntity(mcc = 262, countryName = "Germany", countryCode = "DE"))
        dao.insert(WhitelistCountryEntity(mcc = 208, countryName = "France", countryCode = "FR"))
        dao.insert(WhitelistCountryEntity(mcc = 214, countryName = "Spain", countryCode = "ES"))

        val result = dao.getAll().first()

        assertEquals("France", result[0].countryName)
        assertEquals("Germany", result[1].countryName)
        assertEquals("Spain", result[2].countryName)
    }
}
