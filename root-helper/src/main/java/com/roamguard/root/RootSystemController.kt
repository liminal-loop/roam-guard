package com.roamguard.root

import com.roamguard.common.system.SystemNetworkController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootSystemController @Inject constructor(
    private val rootHelper: RootHelper
) : SystemNetworkController {

    override fun forceManualNetworkSelection(mccMnc: String?): Boolean {
        if (!rootHelper.isAvailable) return false
        return if (mccMnc != null) {
            rootHelper.setManualNetworkSelection(mccMnc)
        } else {
            rootHelper.setAutomaticNetworkSelection()
        }
    }

    override fun setDataRoaming(enabled: Boolean): Boolean {
        if (!rootHelper.isAvailable) return false
        return rootHelper.setDataRoaming(enabled)
    }

    override fun getCurrentNetworkCountry(): String? {
        if (!rootHelper.isAvailable) return null
        return rootHelper.getCurrentNetworkCountry()
    }
}
