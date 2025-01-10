package com.finalproject.domain.model.payment

import java.util.Date

data class CouponModel(
    val orderNo: String = "",
    val date: Date = Date(),
    val couponCode: String = ""
)
