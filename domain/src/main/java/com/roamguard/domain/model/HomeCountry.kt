package com.roamguard.domain.model

data class HomeCountry(
    val id: Long = 0,
    val mcc: Int,
    val countryName: String,
    val countryCode: String
)
