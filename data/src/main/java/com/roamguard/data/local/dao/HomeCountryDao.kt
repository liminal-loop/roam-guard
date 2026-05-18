package com.roamguard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roamguard.data.local.entity.HomeCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeCountryDao {

    @Query("SELECT * FROM home_country LIMIT 1")
    fun getHomeCountry(): Flow<HomeCountryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setHomeCountry(country: HomeCountryEntity)

    @Query("DELETE FROM home_country")
    suspend fun clearHomeCountry()
}
