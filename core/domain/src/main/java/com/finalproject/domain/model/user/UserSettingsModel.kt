package com.finalproject.domain.model.user


data class UserSettings(
    val username: String = "",
    val userPhone: String = "",
    val userMail: String = "",
    val profilePhotoUrl: String? = null
)
