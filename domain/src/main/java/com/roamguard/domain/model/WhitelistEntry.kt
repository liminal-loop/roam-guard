package com.roamguard.domain.model

data class WhitelistEntry(
    val id: Long = 0,
    val mcc: Int,
    val countryName: String,
    val countryCode: String
)
