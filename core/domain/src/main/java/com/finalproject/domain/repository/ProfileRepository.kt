package com.finalproject.domain.repository

import com.finalproject.domain.model.user.AddressModel
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.domain.model.user.UserSettings

interface ProfileRepository {
    suspend fun saveCard(card: CardsModel)
    suspend fun getCards(): List<CardsModel>

    suspend fun saveAddress(address: AddressModel)
    suspend fun getAddresses(): List<AddressModel>

    suspend fun saveUserSettings(userSettings: UserSettings)
    suspend fun getUserSettings(): UserSettings?

}
