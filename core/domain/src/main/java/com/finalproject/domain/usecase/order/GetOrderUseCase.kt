package com.finalproject.domain.usecase.order

import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(): Flow<List<OrderModel>> = flow {
        try {
            val orders = orderRepository.getOrders()
            emit(orders)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
