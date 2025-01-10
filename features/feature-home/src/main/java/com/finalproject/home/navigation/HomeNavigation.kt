package com.finalproject.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.home.HomeScreen


fun NavGraphBuilder.homeNavigation(navController: NavHostController) {
    composable("home_screen") {
        HomeScreen(navController = navController)
    }
}
