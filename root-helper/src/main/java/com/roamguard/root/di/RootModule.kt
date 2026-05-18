package com.roamguard.root.di

import com.roamguard.root.RootCommandExecutor
import com.roamguard.root.RootHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RootModule {

    @Provides
    @Singleton
    fun provideRootCommandExecutor(): RootCommandExecutor {
        return RootCommandExecutor()
    }

    @Provides
    @Singleton
    fun provideRootHelper(executor: RootCommandExecutor): RootHelper {
        return RootHelper(executor)
    }
}
