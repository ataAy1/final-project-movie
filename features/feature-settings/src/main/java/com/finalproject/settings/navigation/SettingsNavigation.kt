package com.finalproject.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.settings.SettingsScreen


fun NavGraphBuilder.settingsNavigation(navController: NavHostController) {
    composable("settings_screen") {
        SettingsScreen(navController = navController)
    }
}

