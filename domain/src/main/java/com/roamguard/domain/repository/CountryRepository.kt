package com.roamguard.domain.repository

import com.roamguard.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    val allCountries: Flow<List<Country>>
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountryByMcc(mcc: Int): Country?
    suspend fun getCountryName(mcc: Int): String
    suspend fun getCountryCode(mcc: Int): String
}
