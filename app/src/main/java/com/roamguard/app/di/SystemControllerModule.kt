package com.roamguard.app.di

import com.roamguard.common.system.MonitoringOnlyController
import com.roamguard.common.system.SystemNetworkController
import com.roamguard.root.RootHelper
import com.roamguard.root.RootSystemController
import com.roamguard.shizuku.ShizukuHelper
import com.roamguard.shizuku.ShizukuSystemController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemControllerModule {

    @Provides
    @Singleton
    fun provideSystemNetworkController(
        rootHelper: RootHelper,
        rootController: RootSystemController,
        shizukuHelper: ShizukuHelper,
        shizukuController: ShizukuSystemController
    ): SystemNetworkController {
        if (rootHelper.isAvailable) {
            return rootController
        }
        if (shizukuHelper.isAvailable) {
            return shizukuController
        }
        return MonitoringOnlyController()
    }
}
