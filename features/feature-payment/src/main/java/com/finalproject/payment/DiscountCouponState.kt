package com.finalproject.payment

import com.finalproject.domain.model.payment.DiscountCouponModel


data class DiscountCouponState(
    val isLoading: Boolean = false,
    val discountCoupons: List<DiscountCouponModel> = emptyList(),
    val error: String? = null
)
