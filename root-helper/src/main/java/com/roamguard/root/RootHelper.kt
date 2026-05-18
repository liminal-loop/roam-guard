package com.roamguard.root

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootHelper @Inject constructor(
    private val executor: RootCommandExecutor
) {
    val isAvailable: Boolean by lazy { executor.isRootAvailable() }

    fun setDataRoaming(enabled: Boolean): Boolean {
        if (!isAvailable) return false
        return executor.setDataRoaming(enabled)
    }

    fun setManualNetworkSelection(plmn: String): Boolean {
        if (!isAvailable) return false
        return executor.setManualNetworkSelection(plmn)
    }

    fun setAutomaticNetworkSelection(): Boolean {
        if (!isAvailable) return false
        return executor.setAutomaticNetworkSelection()
    }

    fun getCurrentNetworkMcc(): Int? {
        if (!isAvailable) return null
        return executor.getCurrentNetworkMcc()
    }

    fun executeRawCommand(command: String): RootResult {
        if (!isAvailable) return RootResult(false, "", "Root not available", -1)
        return executor.executeCommand(command)
    }
}
