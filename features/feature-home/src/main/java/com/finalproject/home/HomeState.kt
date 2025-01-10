package com.finalproject.home

import com.finalproject.domain.model.movie.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val movies: List<Movie>? = null,
    val error: String? = null
)
