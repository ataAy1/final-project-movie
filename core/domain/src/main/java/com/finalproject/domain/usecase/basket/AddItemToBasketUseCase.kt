package com.finalproject.domain.usecase.basket

import android.util.Log
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddItemToBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(userName: String, basketItem: BasketItem): Flow<Result<Unit>> {

        return basketRepository.addBasketItem(userName, basketItem)
    }
}
