package com.finalproject.favorites.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.favorites.FavoritesScreen


fun NavGraphBuilder.favoritesNavigation(navController: NavHostController) {
    composable("favorites_screen") {
        FavoritesScreen(navController = navController)
    }
}
