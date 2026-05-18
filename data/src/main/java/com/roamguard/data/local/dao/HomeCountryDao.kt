package com.roamguard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.roamguard.data.local.entity.HomeCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeCountryDao {

    @Query("SELECT * FROM home_country LIMIT 1")
    fun getHomeCountry(): Flow<HomeCountryEntity?>

    @Transaction
    suspend fun setHomeCountry(country: HomeCountryEntity) {
        clearHomeCountry()
        doInsert(country)
    }

    @Insert
    suspend fun doInsert(country: HomeCountryEntity)

    @Query("DELETE FROM home_country")
    suspend fun clearHomeCountry()
}
