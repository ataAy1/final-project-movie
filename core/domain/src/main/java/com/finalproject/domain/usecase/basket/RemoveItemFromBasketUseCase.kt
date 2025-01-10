package com.finalproject.domain.usecase.basket

import com.finalproject.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveItemFromBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(cartId: Int, userName: String): Flow<Result<Unit>> {
        return basketRepository.removeBasketItem(cartId, userName)
    }
}
