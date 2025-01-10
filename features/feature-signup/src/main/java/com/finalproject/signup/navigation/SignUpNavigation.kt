package com.finalproject.signup.navigation

import android.window.SplashScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.signup.SignUpScreen


fun NavGraphBuilder.signUpNavigation(navController: NavHostController) {
    composable("signup_screen") {
        SignUpScreen(navController = navController)
    }


}
