package com.roamguard.common.system

interface SystemNetworkController {
    fun forceManualNetworkSelection(mccMnc: String?): Boolean
    fun setDataRoaming(enabled: Boolean): Boolean
    fun getCurrentNetworkCountry(): String?
}
