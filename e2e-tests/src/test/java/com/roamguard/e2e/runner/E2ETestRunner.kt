package com.roamguard.e2e.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class E2ETestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, className, context)
    }

    override fun onStart() {
        clearAppData()
        super.onStart()
    }

    private fun clearAppData() {
        try {
            Runtime.getRuntime().exec(arrayOf("sh", "-c", "pm clear com.roamguard.app"))
        } catch (_: Exception) {}
    }
}
