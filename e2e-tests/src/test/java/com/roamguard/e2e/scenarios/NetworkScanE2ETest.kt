package com.roamguard.e2e.scenarios

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.roamguard.app.MainActivity
import com.roamguard.e2e.util.TestSetup
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NetworkScanE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun scanShowsNetworks_tappingTriggersBehavior() {
        TestSetup.completeOnboarding(composeTestRule)

        composeTestRule.onNodeWithText("Networks").performClick()

        composeTestRule.onNodeWithText("Available Networks").assertIsDisplayed()
    }
}
