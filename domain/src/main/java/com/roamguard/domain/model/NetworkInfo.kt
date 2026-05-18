package com.roamguard.domain.model

data class NetworkInfo(
    val mcc: Int,
    val mnc: Int,
    val countryName: String,
    val operatorName: String,
    val plmn: String,
    val isRoaming: Boolean,
    val isHomeNetwork: Boolean = false,
    val isWhitelisted: Boolean = false
)
