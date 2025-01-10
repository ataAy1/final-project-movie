package com.finalproject.movie.detail

data class MovieDetailToFavoriteState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
