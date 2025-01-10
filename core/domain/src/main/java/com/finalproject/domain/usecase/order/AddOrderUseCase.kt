package com.finalproject.domain.usecase.order

import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(order: OrderModel): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(repository.saveOrder(order)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
