package com.roamguard.app.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamguard.domain.model.WhitelistCountry
import com.roamguard.domain.usecase.ManageWhitelistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val manageWhitelistUseCase: ManageWhitelistUseCase
) : ViewModel() {

    val whitelist: StateFlow<List<WhitelistCountry>> = manageWhitelistUseCase.whitelist
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFromWhitelist(mcc: Int) {
        viewModelScope.launch {
            manageWhitelistUseCase.removeFromWhitelist(mcc)
        }
    }
}
