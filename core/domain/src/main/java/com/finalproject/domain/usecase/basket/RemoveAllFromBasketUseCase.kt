package com.finalproject.domain.usecase.basket

import android.util.Log
import com.finalproject.domain.repository.BasketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoveAllFromBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(userName: String, cartIds: List<String>): Flow<Result<Unit>> = flow {
        try {
            cartIds.forEach { cartId ->
                val cartIdInt = cartId.toIntOrNull()
                if (cartIdInt != null) {
                    val result = basketRepository.removeAllBasketItems(userName, cartIdInt)
                    result.collect { flowResult ->
                        if (flowResult.isFailure) {
                        } else {
                        }
                    }
                } else {
                }
            }



            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
