package com.finalproject.basket

import com.finalproject.domain.model.payment.BasketItem

sealed class BasketState {
    object Idle : BasketState()
    data class Success(val items: List<BasketItem>) : BasketState()
    data class Error(val message: String) : BasketState()
}
