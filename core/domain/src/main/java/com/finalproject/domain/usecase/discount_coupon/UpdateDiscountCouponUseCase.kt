package com.finalproject.domain.usecase.discount_coupon

import com.finalproject.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateDiscountCouponUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun execute(couponId: String) {
        repository.updateDiscountCoupon(couponId)
    }
}
