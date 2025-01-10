package com.finalproject.domain.usecase.coupon

import com.finalproject.domain.model.payment.CouponModel
import com.finalproject.domain.repository.OrderRepository
import javax.inject.Inject

class GetCouponsUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun execute(): List<CouponModel> {
        return repository.getCoupons()
    }
}