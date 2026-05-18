package com.roamguard.e2e.util

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

object TestSetup {

    fun completeOnboarding(composeTestRule: ComposeTestRule) {
        composeTestRule.waitForIdle()
        try {
            composeTestRule.onNodeWithText("Welcome to Roam Guard").assertIsDisplayed()
            composeTestRule.onNodeWithText("Germany (DE)").performClick()
            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.waitForIdle()
        } catch (e: AssertionError) {
            android.util.Log.w("TestSetup", "Onboarding already completed or not shown: ${e.message}")
        }
    }
}
