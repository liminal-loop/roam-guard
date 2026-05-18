package com.roamguard.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roamguard.data.local.dao.HomeCountryDao
import com.roamguard.data.local.dao.WhitelistDao
import com.roamguard.data.local.entity.HomeCountryEntity
import com.roamguard.data.local.entity.WhitelistCountryEntity

@Database(
    entities = [
        HomeCountryEntity::class,
        WhitelistCountryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoamGuardDatabase : RoomDatabase() {
    abstract fun homeCountryDao(): HomeCountryDao
    abstract fun whitelistDao(): WhitelistDao
}
