package com.finalproject.domain.repository

import com.finalproject.domain.model.payment.BasketItem
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun addBasketItem(userName: String, basketItem: BasketItem): Flow<Result<Unit>>
    suspend fun getBasketItems(userName: String): Flow<List<BasketItem>>
    suspend fun removeBasketItem(cartId: Int, userName: String): Flow<Result<Unit>>
    suspend fun removeAllBasketItems(userName: String, cartId: Int): Flow<Result<Unit>>

}
