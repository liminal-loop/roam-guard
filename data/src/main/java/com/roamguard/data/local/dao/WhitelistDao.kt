package com.roamguard.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roamguard.data.local.entity.WhitelistCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WhitelistDao {

    @Query("SELECT * FROM whitelist_country ORDER BY country_name ASC")
    fun getAll(): Flow<List<WhitelistCountryEntity>>

    @Query("SELECT * FROM whitelist_country WHERE mcc = :mcc LIMIT 1")
    suspend fun getByMcc(mcc: Int): WhitelistCountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: WhitelistCountryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<WhitelistCountryEntity>)

    @Delete
    suspend fun delete(country: WhitelistCountryEntity)

    @Query("DELETE FROM whitelist_country WHERE mcc = :mcc")
    suspend fun deleteByMcc(mcc: Int)

    @Query("SELECT COUNT(*) FROM whitelist_country WHERE mcc = :mcc")
    suspend fun exists(mcc: Int): Int
}
