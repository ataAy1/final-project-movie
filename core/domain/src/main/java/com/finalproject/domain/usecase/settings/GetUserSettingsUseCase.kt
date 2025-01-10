package com.finalproject.domain.usecase.settings

import android.util.Log
import com.finalproject.domain.model.user.UserSettings
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserSettingsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): UserSettings? {

        return repository.getUserSettings()
    }
}
