package com.finalproject.movie.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailToBasketState(
    val isLoading: Boolean = false,
    var successMessage: String? = null,
    val errorMessage: String? = null
)
