package com.roamguard.common.system

class MonitoringOnlyController : SystemNetworkController {
    override fun forceManualNetworkSelection(mccMnc: String?): Boolean = false
    override fun setDataRoaming(enabled: Boolean): Boolean = false
    override fun getCurrentNetworkCountry(): String? = null
}
