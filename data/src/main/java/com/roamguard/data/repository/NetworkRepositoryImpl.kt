package com.roamguard.data.repository

import android.content.Context
import android.telephony.TelephonyManager
import com.roamguard.common.system.SystemNetworkController
import com.roamguard.common.util.MccCountryMapper
import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.WhitelistRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val whitelistRepository: WhitelistRepository,
    private val systemController: SystemNetworkController
) : NetworkRepository {

    private val telephonyManager: TelephonyManager? by lazy {
        context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
    }

    private val _currentNetwork = MutableStateFlow<NetworkInfo?>(null)
    override val currentNetwork: Flow<NetworkInfo?> = _currentNetwork.asStateFlow()

    private val _availableNetworks = MutableStateFlow<List<NetworkInfo>>(emptyList())
    override val availableNetworks: Flow<List<NetworkInfo>> = _availableNetworks.asStateFlow()

    override suspend fun getCurrentNetwork(): NetworkInfo? {
        val tm = telephonyManager ?: return null
        return try {
            val operator = tm.networkOperator ?: return null
            val mcc = operator.substring(0, 3).toIntOrNull() ?: return null
            val mnc = operator.substring(3).toIntOrNull() ?: 0
            val countryCode = MccCountryMapper.getCountryCode(mcc)
            val countryName = MccCountryMapper.getCountryName(mcc)
            val isRoaming = tm.isNetworkRoaming
            val homeCountry = getHomeCountryMcc()
            val isWhitelisted = whitelistRepository.isWhitelisted(mcc)

            NetworkInfo(
                mcc = mcc,
                mnc = mnc,
                countryName = countryName,
                operatorName = tm.networkOperatorName ?: "",
                plmn = operator,
                isRoaming = isRoaming,
                isHomeNetwork = mcc == homeCountry,
                isWhitelisted = isWhitelisted
            ).also { _currentNetwork.value = it }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun scanAvailableNetworks(): List<NetworkInfo> {
        val tm = telephonyManager ?: return emptyList()
        return try {
            val results = mutableListOf<NetworkInfo>()
            val homeCountryMcc = getHomeCountryMcc()
            val scanResults = tm.javaClass.getMethod("getScanResults").invoke(tm)
            if (scanResults is List<*>) {
                @Suppress("UNCHECKED_CAST")
                (scanResults as List<Any>).forEach { result ->
                    try {
                        val mccClass = result.javaClass
                        val operatorNumeric = mccClass.getMethod("getOperatorNumeric").invoke(result) as? String
                        val operatorName = mccClass.getMethod("getOperatorName").invoke(result) as? String
                        if (operatorNumeric != null && operatorNumeric.length >= 3) {
                            val mcc = operatorNumeric.substring(0, 3).toIntOrNull() ?: return@forEach
                            results.add(
                                NetworkInfo(
                                    mcc = mcc,
                                    mnc = 0,
                                    countryName = MccCountryMapper.getCountryName(mcc),
                                    operatorName = operatorName ?: "",
                                    plmn = operatorNumeric,
                                    isRoaming = mcc != homeCountryMcc,
                                    isHomeNetwork = mcc == homeCountryMcc,
                                    isWhitelisted = whitelistRepository.isWhitelisted(mcc)
                                )
                            )
                        }
                    } catch (_: Exception) {}
                }
            }
            results.toList().also { _availableNetworks.value = it }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun connectToNetwork(plmn: String): Boolean {
        return systemController.forceManualNetworkSelection(plmn)
    }

    override suspend fun setDataRoamingEnabled(enabled: Boolean): Boolean {
        return systemController.setDataRoaming(enabled)
    }

    override suspend fun evaluateRoaming(): RoamingDecision {
        val current = getCurrentNetwork() ?: return RoamingDecision.Denied("No network")
        val homeCountryMcc = getHomeCountryMcc()

        if (!current.isRoaming || current.mcc == homeCountryMcc) {
            return RoamingDecision.Allowed
        }

        if (current.isWhitelisted) {
            return RoamingDecision.Allowed
        }

        return RoamingDecision.NeedsConfirmation(
            network = current,
            country = current.countryName
        )
    }

    override suspend fun recheckAndReconnect(): Boolean {
        val current = getCurrentNetwork() ?: return false
        val homeCountryMcc = getHomeCountryMcc()

        if (current.mcc == homeCountryMcc) return true
        if (current.isWhitelisted) return true

        val networks = scanAvailableNetworks()
        val homeOrAllowed = networks.firstOrNull {
            it.mcc == homeCountryMcc || it.isWhitelisted
        }

        return if (homeOrAllowed != null) {
            connectToNetwork(homeOrAllowed.plmn)
        } else {
            setDataRoamingEnabled(false)
            false
        }
    }

    private suspend fun getHomeCountryMcc(): Int? {
        return whitelistRepository.homeCountry.first()?.mcc
    }
}
