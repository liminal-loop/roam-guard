package com.roamguard.shizuku

import android.content.Context
import com.roamguard.common.system.SystemNetworkController
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShizukuSystemController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shizukuHelper: ShizukuHelper
) : SystemNetworkController {

    private var initialized = false

    private fun ensureInitialized() {
        if (!initialized) {
            shizukuHelper.init(context)
            initialized = true
        }
    }

    override fun forceManualNetworkSelection(mccMnc: String?): Boolean {
        ensureInitialized()
        if (!shizukuHelper.isAvailable) return false
        return if (mccMnc != null) {
            shizukuHelper.setNetworkSelectionModeManual(mccMnc)
        } else {
            shizukuHelper.setNetworkSelectionModeAutomatic()
        }
    }

    override fun setDataRoaming(enabled: Boolean): Boolean {
        ensureInitialized()
        if (!shizukuHelper.isAvailable) return false
        return shizukuHelper.setDataRoamingEnabled(0, enabled)
    }

    override fun getCurrentNetworkCountry(): String? {
        ensureInitialized()
        if (!shizukuHelper.isAvailable) return null
        return shizukuHelper.getCurrentNetworkCountry()
    }
}
