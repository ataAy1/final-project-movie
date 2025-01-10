package com.finalproject.profile

import com.finalproject.domain.model.user.UserSettings

data class ProfileState(
    val userSettings: UserSettings? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)