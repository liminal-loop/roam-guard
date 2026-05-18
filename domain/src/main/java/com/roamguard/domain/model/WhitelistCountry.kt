package com.roamguard.domain.model

data class WhitelistCountry(
    val id: Long = 0,
    val mcc: Int,
    val countryName: String,
    val countryCode: String
)
