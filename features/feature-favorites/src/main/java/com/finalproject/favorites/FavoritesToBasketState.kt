package com.finalproject.favorites

data class FavoritesToBasketState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
