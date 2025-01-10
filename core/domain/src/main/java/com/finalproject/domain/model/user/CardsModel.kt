package com.finalproject.domain.model.user

data class CardsModel(
    val cardId: String,
    val cardName: String,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCVV: String
)
