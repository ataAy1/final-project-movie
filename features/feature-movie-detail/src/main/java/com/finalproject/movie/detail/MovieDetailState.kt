package com.finalproject.movie.detail

import com.finalproject.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Flow<Movie> = emptyFlow(),
    val error: String? = null,
    val success: String? = null

)
