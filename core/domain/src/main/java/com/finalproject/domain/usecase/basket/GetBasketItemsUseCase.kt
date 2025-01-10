package com.finalproject.domain.usecase.basket

import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBasketItemsUseCase @Inject constructor(
    private val repository: BasketRepository
) {
    suspend operator fun invoke(userName: String): Flow<List<BasketItem>> {
        return repository.getBasketItems(userName)
    }
}
