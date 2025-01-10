package com.finalproject.data.mapper

import com.finalproject.data.model.BasketResponse
import com.finalproject.data.model.BasketItem as DataBasketItem
import com.finalproject.domain.model.payment.BasketItem

class BasketMapper {

    fun toDomain(basketResponse: BasketResponse): List<BasketItem> {
        return basketResponse.movie_cart?.map { basketItem ->
            BasketItem(
                cartId = basketItem.cartId,
                name = basketItem.name,
                image = basketItem.image,
                price = basketItem.price,
                category = basketItem.category,
                rating = basketItem.rating,
                year = basketItem.year,
                director = basketItem.director,
                description = basketItem.description,
                orderAmount = basketItem.orderAmount,
                userName = basketItem.userName
            )
        } ?: emptyList()
    }


    fun toResponse(basketItem: BasketItem): DataBasketItem {
        return DataBasketItem(
            cartId = basketItem.cartId,
            name = basketItem.name,
            image = basketItem.image,
            price = basketItem.price,
            category = basketItem.category,
            rating = basketItem.rating,
            year = basketItem.year,
            director = basketItem.director,
            description = basketItem.description,
            orderAmount = basketItem.orderAmount,
            userName = basketItem.userName
        )
    }

}
