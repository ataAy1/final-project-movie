package com.finalproject.domain.usecase.profile.address

import android.util.Log
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): List<AddressModel> {
        return profileRepository.getAddresses()
    }
}
