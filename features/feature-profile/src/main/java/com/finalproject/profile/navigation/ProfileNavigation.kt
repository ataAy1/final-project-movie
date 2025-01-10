package com.finalproject.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.profile.ProfileScreen


fun NavGraphBuilder.profileNavigation(navController: NavHostController) {
    composable("profile_screen") {
        ProfileScreen(navController = navController)
    }
}
