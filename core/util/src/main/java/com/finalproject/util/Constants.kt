package com.finalproject.util

object Constants {

    const val BASE_URL = "http://kasimadalan.pe.hu/"
    const val IMAGE_BASE_URL = "http://kasimadalan.pe.hu/movies/images/"

    const val CARGO_DELIVERY_PRICE = 15

    fun getImageUrl(imagePath: String): String {
        return "${Constants.IMAGE_BASE_URL}$imagePath"
    }
}
