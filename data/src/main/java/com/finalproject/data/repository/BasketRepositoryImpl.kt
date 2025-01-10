package com.finalproject.data.repository

import com.finalproject.data.mapper.BasketMapper
import com.finalproject.data.remote.ApiService
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import android.util.Log
import java.io.EOFException
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: BasketMapper
) : BasketRepository {

    private val TAG = "BasketRepositoryImpl"

    override suspend fun addBasketItem(userName: String, basketItem: BasketItem): Flow<Result<Unit>> = flow {
        try {
            val apiResponse = apiService.addMovie(
                name = basketItem.name,
                image = basketItem.image,
                price = basketItem.price,
                category = basketItem.category,
                rating = basketItem.rating,
                year = basketItem.year,
                director = basketItem.director,
                description = basketItem.description,
                orderAmount = basketItem.orderAmount,
                userName = basketItem.userName
            )

            Log.d(TAG, "API response: $apiResponse")

            if (apiResponse.success == 1) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("API call failed: ${apiResponse.message}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }



    override suspend fun getBasketItems(userName: String): Flow<List<BasketItem>> = flow {
        try {

            val basketResponse = apiService.getMoviesCart(userName)

            if (basketResponse.movie_cart != null && basketResponse.movie_cart.isNotEmpty()) {
                val basketItems = mapper.toDomain(basketResponse)
                emit(basketItems)
            } else {
                emit(emptyList<BasketItem>())
            }
        } catch (e: EOFException) {
            emit(emptyList<BasketItem>())
        } catch (e: Exception) {
            emit(emptyList<BasketItem>())
        }
    }




    override suspend fun removeBasketItem(cartId: Int, userName: String): Flow<Result<Unit>> = flow {
        try {
            val response = apiService.removeBasketItem(cartId, userName)
            if (response.success == 1) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun removeAllBasketItems(userName: String, cartId: Int): Flow<Result<Unit>> = flow {
        try {
            val response = apiService.removeBasketItem(cartId, userName)
            if (response.success == 1) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


}
