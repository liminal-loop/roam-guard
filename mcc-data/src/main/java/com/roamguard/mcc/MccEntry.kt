package com.roamguard.mcc

data class MccEntry(
    val mcc: Int,
    val mnc: Int? = null,
    val countryCode: String,
    val countryName: String,
    val operatorName: String? = null
)
