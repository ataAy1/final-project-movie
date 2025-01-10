package com.finalproject.movie.detail.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finalproject.movie.detail.MovieDetailScreen
import com.finalproject.domain.model.movie.Movie

fun NavGraphBuilder.movieDetailNavigation(navController: NavHostController) {
    composable(
        "movie_detail_screen/{movieId}",
        arguments = listOf(
            navArgument("movieId") {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt("movieId")
        Log.d("MovieDetailNavigation", "Received movie ID: $movieId")
        if (movieId != null) {
            MovieDetailScreen(movieId = movieId, navController = navController)
        }
    }
}
