package com.roamguard.shizuku.di

import com.roamguard.shizuku.ShizukuHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShizukuModule {

    @Provides
    @Singleton
    fun provideShizukuHelper(): ShizukuHelper {
        return ShizukuHelper()
    }
}
