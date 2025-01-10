package com.finalproject.domain.model.user

import java.util.Date


data class UserModel(
    val email: String,
    val name: String,
    val surname: String,
    val phone: String,
    val dob: Date,
    val profilePhoto: Int
)
