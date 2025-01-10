package com.finalproject.favorites

import com.finalproject.domain.model.movie.Movie

data class FavoritesState(
    val favoriteMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


