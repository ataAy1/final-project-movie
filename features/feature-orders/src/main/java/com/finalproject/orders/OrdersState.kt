package com.finalproject.orders

import com.finalproject.domain.model.payment.OrderModel

sealed class OrdersState {
    object Idle : OrdersState()
    object Loading : OrdersState()
    object Empty : OrdersState()
    data class Success(val orders: List<OrderModel>) : OrdersState()
    data class Error(val message: String) : OrdersState()
}