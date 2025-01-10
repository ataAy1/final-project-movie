package com.finalproject.feature.navigation

import android.window.SplashScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.feature.SplashMovieScreen
import com.finalproject.feature.WelcomeScreen


fun NavGraphBuilder.welcomeNavigation(navController: NavHostController) {
    composable("welcome_screen") {
        WelcomeScreen(navController = navController)
    }

    composable("splash_screen") {
        SplashMovieScreen(navController = navController)
    }
}
