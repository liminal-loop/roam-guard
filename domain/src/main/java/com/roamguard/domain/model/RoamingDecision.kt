package com.roamguard.domain.model

sealed class RoamingDecision {
    data object Allowed : RoamingDecision()
    data class Denied(val reason: String) : RoamingDecision()
    data class NeedsConfirmation(
        val network: NetworkInfo,
        val country: String
    ) : RoamingDecision()
}
