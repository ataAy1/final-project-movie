package com.finalproject.coupon

import com.finalproject.domain.model.payment.CouponModel

data class CouponState(
    val isLoading: Boolean = false,
    val coupons: List<CouponModel> = emptyList(),
    val error: String? = null
)
