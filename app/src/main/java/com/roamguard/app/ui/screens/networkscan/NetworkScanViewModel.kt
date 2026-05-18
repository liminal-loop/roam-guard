package com.roamguard.app.ui.screens.networkscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamguard.domain.model.NetworkInfo
import com.roamguard.domain.usecase.ScanNetworksUseCase
import com.roamguard.domain.usecase.ConnectToNetworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkScanViewModel @Inject constructor(
    private val scanNetworksUseCase: ScanNetworksUseCase,
    private val connectToNetworkUseCase: ConnectToNetworkUseCase
) : ViewModel() {

    private val _networks = MutableStateFlow<List<NetworkInfo>>(emptyList())
    val networks: StateFlow<List<NetworkInfo>> = _networks.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _connectResult = MutableStateFlow<String?>(null)
    val connectResult: StateFlow<String?> = _connectResult.asStateFlow()

    init {
        scan()
    }

    fun scan() {
        viewModelScope.launch {
            _isScanning.value = true
            val result = scanNetworksUseCase()
            _networks.value = result
            _isScanning.value = false
        }
    }

    fun connectToNetwork(plmn: String) {
        viewModelScope.launch {
            val success = connectToNetworkUseCase(plmn)
            _connectResult.value = if (success) "Connected successfully" else "Connection failed"
        }
    }

    fun clearConnectResult() {
        _connectResult.value = null
    }
}
