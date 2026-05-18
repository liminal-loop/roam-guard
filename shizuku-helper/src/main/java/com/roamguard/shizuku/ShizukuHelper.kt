package com.roamguard.shizuku

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShizukuHelper @Inject constructor() {

    private val reflector = ShizukuApiReflector()
    private var initialized = false

    val isAvailable: Boolean
        get() = try {
            Class.forName("rikka.shizuku.Shizuku")
            true
        } catch (e: Exception) {
            false
        }

    fun init(context: Context) {
        if (!initialized) {
            reflector.init(context)
            initialized = true
        }
    }

    fun setNetworkSelectionModeManual(plmn: String, subId: Int = 0): Boolean {
        if (!isAvailable) return false
        return reflector.setNetworkSelectionModeManual(plmn, subId)
    }

    fun setNetworkSelectionModeAutomatic(): Boolean {
        if (!isAvailable) return false
        return reflector.setNetworkSelectionModeAutomatic()
    }

    fun setDataRoamingEnabled(subId: Int, enabled: Boolean): Boolean {
        if (!isAvailable) return false
        return reflector.setDataRoamingEnabled(subId, enabled)
    }

    fun getCurrentNetworkMcc(): Int? {
        if (!isAvailable) return null
        return reflector.getNetworkOperatorMcc()
    }

    fun getCurrentNetworkCountry(): String? {
        if (!isAvailable) return null
        return reflector.getNetworkCountryIso()
    }

    fun isNetworkRoaming(): Boolean {
        if (!isAvailable) return false
        return reflector.isNetworkRoaming()
    }
}
