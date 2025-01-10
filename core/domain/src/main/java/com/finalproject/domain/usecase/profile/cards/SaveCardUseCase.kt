package com.finalproject.domain.usecase.profile.cards

import com.finalproject.domain.model.user.CardsModel
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveCardUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(card: CardsModel) {
        repository.saveCard(card)
    }
}
