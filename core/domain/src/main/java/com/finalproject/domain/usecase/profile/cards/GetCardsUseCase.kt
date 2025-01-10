package com.finalproject.domain.usecase.profile.cards

import com.finalproject.domain.model.user.CardsModel
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): List<CardsModel> {
        return repository.getCards()
    }
}
