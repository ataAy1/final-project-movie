package com.finalproject.domain.usecase.settings

import com.finalproject.domain.model.user.UserSettings
import com.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveUserSettingsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userSettings: UserSettings) {
        repository.saveUserSettings(userSettings)
    }
}
