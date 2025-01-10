package com.finalproject.domain.repository

import com.finalproject.domain.model.payment.CouponModel
import com.finalproject.domain.model.payment.DiscountCouponModel
import com.finalproject.domain.model.payment.OrderModel

interface OrderRepository {
    suspend fun saveOrder(order: OrderModel)
    suspend fun getOrders(): List<OrderModel>

    suspend fun saveCoupon(coupon: CouponModel)
    suspend fun getCoupons(): List<CouponModel>

    suspend fun saveDiscountCoupon(coupon: DiscountCouponModel)
    suspend fun getDiscountCoupons(): List<DiscountCouponModel>
    suspend fun updateDiscountCoupon(couponId: String)

}
