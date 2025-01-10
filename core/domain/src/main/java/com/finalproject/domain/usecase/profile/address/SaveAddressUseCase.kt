package com.finalproject.domain.usecase.profile.address

import com.finalproject.domain.model.user.AddressModel
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(address: AddressModel) {
        profileRepository.saveAddress(address)
    }
}
