package com.finalproject

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object MovieDetail : Screen("movie_detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail_screen/$movieId"
    }
    object Basket : Screen("basket_screen")
}
