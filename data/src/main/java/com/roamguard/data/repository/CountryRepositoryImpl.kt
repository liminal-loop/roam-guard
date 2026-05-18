package com.roamguard.data.repository

import com.roamguard.data.local.mcc.MccDatasetLoader
import com.roamguard.domain.model.Country
import com.roamguard.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val mccDatasetLoader: MccDatasetLoader
) : CountryRepository {

    private val _allCountries = MutableStateFlow<List<Country>>(emptyList())

    override val allCountries: Flow<List<Country>> = _allCountries.asStateFlow()

    override suspend fun getAllCountries(): List<Country> {
        val entries = mccDatasetLoader.getEntries()
        return entries.map {
            Country(
                mcc = it.mcc,
                countryName = it.countryName,
                countryCode = it.countryCode
            )
        }.distinctBy { it.mcc }.sortedBy { it.countryName }.also { _allCountries.value = it }
    }

    override suspend fun getCountryByMcc(mcc: Int): Country? {
        val entry = mccDatasetLoader.getEntries().firstOrNull { it.mcc == mcc }
        return entry?.let {
            Country(mcc = it.mcc, countryName = it.countryName, countryCode = it.countryCode)
        }
    }

    override suspend fun getCountryName(mcc: Int): String {
        return getCountryByMcc(mcc)?.countryName ?: "Unknown"
    }

    override suspend fun getCountryCode(mcc: Int): String {
        return getCountryByMcc(mcc)?.countryCode ?: "UNKNOWN"
    }
}
