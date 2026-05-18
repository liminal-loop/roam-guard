package com.roamguard.app.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamguard.common.util.MccCountryMapper
import com.roamguard.domain.model.HomeCountry
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val whitelistRepository: WhitelistRepository
) : ViewModel() {

    val isOnboardingComplete = settingsRepository.isOnboardingComplete
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _selectedMcc = MutableStateFlow<Int?>(null)
    val selectedMcc: StateFlow<Int?> = _selectedMcc.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isComplete = MutableStateFlow(false)
    val isComplete: StateFlow<Boolean> = _isComplete.asStateFlow()

    val countries = MccCountryMapper.getAllMccEntries()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCountry(mcc: Int) {
        _selectedMcc.value = mcc
    }

    fun completeOnboarding() {
        val mcc = _selectedMcc.value ?: return
        viewModelScope.launch {
            val entry = MccCountryMapper.getAllMccEntries().firstOrNull { it.mcc == mcc } ?: return@launch
            whitelistRepository.setHomeCountry(
                HomeCountry(
                    mcc = mcc,
                    countryName = entry.countryName,
                    countryCode = entry.countryCode
                )
            )
            settingsRepository.setOnboardingComplete(true)
            _isComplete.value = true
        }
    }

    fun skipOnboarding() {
        viewModelScope.launch {
            settingsRepository.setOnboardingComplete(true)
            _isComplete.value = true
        }
    }
}
