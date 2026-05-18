package com.roamguard.root

import java.io.BufferedReader
import java.io.InputStreamReader

class RootCommandExecutor {

    fun isRootAvailable(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("which", "su"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val line = reader.readLine()
            process.waitFor()
            line != null && line.isNotBlank()
        } catch (e: Exception) {
            false
        }
    }

    fun executeCommand(command: String): RootResult {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val stdout = BufferedReader(InputStreamReader(process.inputStream))
            val stderr = BufferedReader(InputStreamReader(process.errorStream))
            val output = stdout.readText()
            val error = stderr.readText()
            val exitCode = process.waitFor()
            RootResult(
                success = exitCode == 0,
                output = output.trim(),
                error = error.trim(),
                exitCode = exitCode
            )
        } catch (e: Exception) {
            RootResult(
                success = false,
                output = "",
                error = e.message ?: "Unknown error",
                exitCode = -1
            )
        }
    }

    fun setDataRoaming(enabled: Boolean): Boolean {
        val value = if (enabled) "1" else "0"
        val result = executeCommand("settings put global data_roaming $value")
        return result.success
    }

    fun setManualNetworkSelection(plmn: String): Boolean {
        val result = executeCommand("cmd phone network-select-mode manual $plmn")
        return result.success
    }

    fun setAutomaticNetworkSelection(): Boolean {
        val result = executeCommand("cmd phone network-select-mode automatic")
        return result.success
    }

    fun getCurrentNetworkMcc(): Int? {
        val result = executeCommand("dumpsys telephony.registry | awk '/mMcc/{print \$2}'")
        if (result.success && result.output.isNotBlank()) {
            return result.output.trim().toIntOrNull()
        }
        return null
    }

    fun getCurrentNetworkCountry(): String? {
        val result = executeCommand("dumpsys telephony.registry | awk '/mCountryIso/{print \$2}'")
        if (result.success && result.output.isNotBlank()) {
            return result.output.trim().takeIf { it.length == 2 }
        }
        return null
    }
}

data class RootResult(
    val success: Boolean,
    val output: String,
    val error: String,
    val exitCode: Int
)
