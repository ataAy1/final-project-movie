package com.finalproject.domain.usecase.discount_coupon

import com.finalproject.domain.model.payment.DiscountCouponModel
import com.finalproject.domain.repository.OrderRepository
import javax.inject.Inject

class SaveDiscountCouponUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun execute(coupon: DiscountCouponModel) {
        repository.saveDiscountCoupon(coupon)
    }
}
