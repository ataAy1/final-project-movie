package com.finalproject.basket.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.basket.BasketScreen


fun NavGraphBuilder.basketNavigation(navController: NavHostController) {
    composable("basket_screen") {
        BasketScreen(navController = navController)
    }
}
