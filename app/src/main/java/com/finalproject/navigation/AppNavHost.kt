package com.finalproject.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finalproject.Screen
import com.finalproject.address.navigation.addressNavigation
import com.finalproject.basket.BasketScreen
import com.finalproject.basket.navigation.basketNavigation
import com.finalproject.cards.navigation.cardsNavigation
import com.finalproject.coupon.navigation.couponNavigation
import com.finalproject.favorites.navigation.favoritesNavigation
import com.finalproject.feature.SplashMovieScreen
import com.finalproject.feature.navigation.welcomeNavigation
import com.finalproject.home.HomeScreen
import com.finalproject.home.navigation.homeNavigation
import com.finalproject.movie.detail.MovieDetailScreen
import com.finalproject.movie.detail.navigation.movieDetailNavigation
import com.finalproject.orders.navigation.ordersNavigation
import com.finalproject.profile.navigation.profileNavigation
import com.finalproject.settings.navigation.settingsNavigation
import com.finalproject.signin.navigation.signInNavigation
import com.finalproject.signup.navigation.signUpNavigation
import paymentNavigation


@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash_screen") {

        // SignUp Navigation
        signUpNavigation(navController = navController)

        // SignIn Navigation
        signInNavigation(navController = navController)

        // Welcome Navigation
        welcomeNavigation(navController = navController)

        // Home Navigation
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Home Navigation
        profileNavigation(navController = navController)

        // Orders Navigation
        ordersNavigation(navController = navController)

        // Address Navigation
        addressNavigation(navController = navController)

        // Cards Navigation
        cardsNavigation(navController = navController)

        // Settings Navigation
        settingsNavigation(navController = navController)

        // Movie Detail Navigation
        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            if (movieId != null) {
                MovieDetailScreen(movieId = movieId, navController = navController)
            }
        }

        // Movie Detail Navigation
        favoritesNavigation(navController = navController)

        // Basket Navigation
        composable(Screen.Basket.route) {
            BasketScreen(navController = navController)
        }

        // Payment Navigation
        paymentNavigation(navController = navController)

        // Coupon Navigation
        couponNavigation(navController = navController)

    }
}
