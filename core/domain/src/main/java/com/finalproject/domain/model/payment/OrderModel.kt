package com.finalproject.domain.model.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class OrderModel(
    val name: String = "",
    val orderNo: String = "",
    val image: String = "",
    val movieID: Int = 0,
    val price: Int = 0,
    val discount: Int = 0,
    val orderAmount: Int = 0,
    val totalPrice: Int = 0,
    val userName: String = "",
    val date: Date = Date(),
    val deliveryOption: String = ""
) : Parcelable
