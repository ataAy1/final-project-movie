package com.finalproject.domain.model.user

data class AddressModel(
    val id: String = "",
    val userNameSurname: String = "",
    val userTelephoneNumber: String = "",
    val apartmentNo: String = "",
    val userAddress: String = "",

    val floor: Int = 0,
    val homeNo: String = "",
    val addressType: Int = 0,
    val addressDescription: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
