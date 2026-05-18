package com.roamguard.e2e.scenarios

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.roamguard.app.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class OnboardingE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun userSetsHomeCountryDE_addsAT_IT_toWhitelist() {
        composeTestRule.onNodeWithText("Welcome to Roam Guard").assertIsDisplayed()
        composeTestRule.onNodeWithText("Select your home country").assertIsDisplayed()

        val continueButton = composeTestRule.onNodeWithText("Continue")
        continueButton.assertIsNotEnabled()

        composeTestRule.onNodeWithText("Germany (DE)").performClick()
        continueButton.assertIsEnabled()
        continueButton.performClick()

        composeTestRule.onNodeWithText("Home Country").assertIsDisplayed()
        composeTestRule.onNodeWithText("Germany (MCC: 262)").assertIsDisplayed()
    }
}
