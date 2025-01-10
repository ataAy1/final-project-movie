package com.finalproject.cards

import com.finalproject.domain.model.user.CardsModel

sealed class CardsState {
    object Loading : CardsState()
    object Saving : CardsState()
    data class Success(val cards: List<CardsModel>) : CardsState()
    data class Error(val message: String) : CardsState()
    data class AddSuccess(val isSuccess: Boolean) : CardsState()

}
