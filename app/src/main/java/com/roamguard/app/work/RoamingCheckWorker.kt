package com.roamguard.app.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.roamguard.common.system.SystemNetworkController
import com.roamguard.domain.repository.WhitelistRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RoamingCheckWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val systemController: SystemNetworkController,
    private val whitelistRepository: WhitelistRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val country = systemController.getCurrentNetworkCountry() ?: return Result.retry()
        val homeCountry = whitelistRepository.homeCountry.first() ?: return Result.retry()

        if (country.equals(homeCountry.countryCode, ignoreCase = true)) {
            return Result.success()
        }

        val isWhitelisted = whitelistRepository.whitelist.first().any {
            it.countryCode.equals(country, ignoreCase = true)
        }

        if (isWhitelisted) return Result.success()

        systemController.forceManualNetworkSelection(null)
        return Result.success()
    }
}
