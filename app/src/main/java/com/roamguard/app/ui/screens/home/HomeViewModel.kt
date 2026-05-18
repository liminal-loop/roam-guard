package com.roamguard.app.ui.screens.home

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.roamguard.app.service.RoamingForegroundService
import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.model.RoamingDecision
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import com.roamguard.domain.usecase.CheckRoamingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val checkRoamingUseCase: CheckRoamingUseCase,
    private val networkRepository: NetworkRepository,
    private val whitelistRepository: WhitelistRepository,
    private val settingsRepository: SettingsRepository
) : AndroidViewModel(application) {

    val homeCountry: StateFlow<HomeCountry?> = whitelistRepository.homeCountry
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isServiceEnabled = settingsRepository.isServiceEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _currentDecision = MutableStateFlow<RoamingDecision?>(null)
    val currentDecision: StateFlow<RoamingDecision?> = _currentDecision.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _dialogNetworkMcc = MutableStateFlow<Int?>(null)
    val dialogNetworkMcc: StateFlow<Int?> = _dialogNetworkMcc.asStateFlow()

    private val _dialogCountry = MutableStateFlow("")
    val dialogCountry: StateFlow<String> = _dialogCountry.asStateFlow()

    fun toggleService() {
        val app = getApplication<Application>()
        viewModelScope.launch {
            val current = !isServiceEnabled.value
            settingsRepository.setServiceEnabled(current)
            if (current) {
                val intent = Intent(app, RoamingForegroundService::class.java)
                app.startForegroundService(intent)
            } else {
                val intent = Intent(app, RoamingForegroundService::class.java)
                app.stopService(intent)
            }
        }
    }

    fun checkRoaming() {
        viewModelScope.launch {
            val decision = checkRoamingUseCase()
            _currentDecision.value = decision
            when (decision) {
                is RoamingDecision.NeedsConfirmation -> {
                    _dialogCountry.value = decision.country
                    _dialogNetworkMcc.value = decision.network.mcc
                    _showDialog.value = true
                }
                else -> {}
            }
        }
    }

    fun allowNetwork(alwaysAllow: Boolean, mcc: Int?) {
        _showDialog.value = false
        if (alwaysAllow && mcc != null) {
            viewModelScope.launch {
                whitelistRepository.addToWhitelist(
                    com.roamguard.domain.model.WhitelistCountry(
                        mcc = mcc,
                        countryName = _dialogCountry.value,
                        countryCode = ""
                    )
                )
            }
        }
    }

    fun denyNetwork() {
        _showDialog.value = false
        viewModelScope.launch {
            networkRepository.recheckAndReconnect()
        }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}
