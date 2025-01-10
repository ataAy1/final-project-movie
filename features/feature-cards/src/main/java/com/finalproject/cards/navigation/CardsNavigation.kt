package com.finalproject.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.cards.AddCardScreen
import com.finalproject.cards.CardsScreen


fun NavGraphBuilder.cardsNavigation(navController: NavHostController) {
    composable("cards_screen") {
        CardsScreen(navController = navController)

    }
    composable("add_card_screen") {
        AddCardScreen(navController = navController)
    }
}
