package com.roamguard.data.di

import com.roamguard.data.repository.NetworkRepositoryImpl
import com.roamguard.data.repository.SettingsRepositoryImpl
import com.roamguard.data.repository.WhitelistRepositoryImpl
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindWhitelistRepository(impl: WhitelistRepositoryImpl): WhitelistRepository

    @Binds
    @Singleton
    abstract fun bindNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository
}
