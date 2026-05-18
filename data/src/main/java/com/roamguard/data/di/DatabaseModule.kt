package com.roamguard.data.di

import android.content.Context
import androidx.room.Room
import com.roamguard.common.util.Constants
import com.roamguard.data.local.dao.HomeCountryDao
import com.roamguard.data.local.dao.WhitelistDao
import com.roamguard.data.local.db.RoamGuardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoamGuardDatabase {
        return Room.databaseBuilder(
            context,
            RoamGuardDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHomeCountryDao(database: RoamGuardDatabase): HomeCountryDao {
        return database.homeCountryDao()
    }

    @Provides
    fun provideWhitelistDao(database: RoamGuardDatabase): WhitelistDao {
        return database.whitelistDao()
    }
}
