package com.finalproject.settings

import com.finalproject.domain.model.user.UserSettings

data class SettingsState(
    val userSettings: UserSettings? = null,
    val isLoading: Boolean = false,
    val isSaveSuccessful: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    )
