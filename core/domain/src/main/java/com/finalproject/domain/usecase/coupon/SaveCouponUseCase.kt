package com.finalproject.domain.usecase.coupon

import com.finalproject.domain.model.payment.CouponModel
import com.finalproject.domain.repository.OrderRepository
import javax.inject.Inject

class SaveCouponUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun execute(coupon: CouponModel) {
        repository.saveCoupon(coupon)
    }
}