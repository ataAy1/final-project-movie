package com.finalproject.payment

sealed class PaymentState {
    object Idle : PaymentState()
    object Saving : PaymentState()
    data class Success(val message: String) : PaymentState()
    data class Error(val error: String) : PaymentState()
}