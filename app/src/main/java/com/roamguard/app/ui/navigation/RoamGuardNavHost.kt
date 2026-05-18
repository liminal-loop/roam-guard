package com.roamguard.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roamguard.app.ui.screens.home.HomeScreen
import com.roamguard.app.ui.screens.home.HomeViewModel
import com.roamguard.app.ui.screens.networkscan.NetworkScanScreen
import com.roamguard.app.ui.screens.networkscan.NetworkScanViewModel
import com.roamguard.app.ui.screens.onboarding.OnboardingScreen
import com.roamguard.app.ui.screens.onboarding.OnboardingViewModel
import com.roamguard.app.ui.screens.settings.SettingsScreen
import com.roamguard.app.ui.screens.settings.SettingsViewModel
import com.roamguard.app.ui.screens.whitelist.WhitelistScreen
import com.roamguard.app.ui.screens.whitelist.WhitelistViewModel

object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val WHITELIST = "whitelist"
    const val NETWORK_SCAN = "network_scan"
    const val SETTINGS = "settings"
}

@Composable
fun RoamGuardNavHost() {
    val navController = rememberNavController()
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val onboardingComplete by onboardingViewModel.isOnboardingComplete.collectAsState(initial = null)

    val startDestination = when (onboardingComplete) {
        true -> Routes.HOME
        false -> Routes.ONBOARDING
        null -> null
    }

    if (startDestination == null) return

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                viewModel = hiltViewModel(),
                onComplete = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = hiltViewModel(),
                onNavigateToWhitelist = { navController.navigate(Routes.WHITELIST) },
                onNavigateToNetworkScan = { navController.navigate(Routes.NETWORK_SCAN) },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.WHITELIST) {
            WhitelistScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.NETWORK_SCAN) {
            NetworkScanScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() },
                onNavigateToWhitelist = { navController.navigate(Routes.WHITELIST) }
            )
        }
    }
}
