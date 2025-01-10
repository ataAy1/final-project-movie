package com.finalproject.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.signin.SignInScreen

fun NavGraphBuilder.signInNavigation(navController: NavHostController) {
    composable("signin_screen") {
        SignInScreen(navController = navController)
    }
}
