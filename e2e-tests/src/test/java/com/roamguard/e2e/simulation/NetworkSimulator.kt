package com.roamguard.e2e.simulation

object NetworkSimulator {

    fun simulateNetwork(mccMnc: String, isRoaming: Boolean = false) {
        try {
            Runtime.getRuntime().exec(arrayOf("sh", "-c", "setprop gsm.operator.numeric '$mccMnc'"))
            Runtime.getRuntime().exec(arrayOf("sh", "-c", "setprop gsm.operator.isroaming ${if (isRoaming) "true" else "false"}"))
            Thread.sleep(500)
        } catch (e: Exception) {
            android.util.Log.e("NetworkSimulator", "Failed to simulate network", e)
        }
    }

    fun simulateHomeNetwork() {
        simulateNetwork("26201", false)
    }

    fun simulateAllowedRoaming() {
        simulateNetwork("23201", true)
    }

    fun simulateDeniedRoaming() {
        simulateNetwork("22801", true)
    }

    fun clearNetworkProperties() {
        try {
            Runtime.getRuntime().exec(arrayOf("sh", "-c", "setprop gsm.operator.numeric ''"))
            Runtime.getRuntime().exec(arrayOf("sh", "-c", "setprop gsm.operator.isroaming false"))
        } catch (_: Exception) {}
    }

    fun waitForNetworkChange(millis: Long = 2000) {
        try {
            Thread.sleep(millis)
        } catch (_: InterruptedException) {}
    }
}
