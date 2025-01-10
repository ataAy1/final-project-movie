package com.finalproject.domain.model.payment

import java.util.Date

data class DiscountCouponModel(
    val id: String = "",
    val code: String = "",
    val details: String = "",
    val discountAmount: Int = 0,
    val expirationDate: Date = Date(),
    val active: Boolean = false
)
