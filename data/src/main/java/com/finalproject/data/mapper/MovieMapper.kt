package com.finalproject.data.mapper

import com.finalproject.data.model.MovieResponseItem
import com.finalproject.data.entity.MovieEntity
import com.finalproject.domain.model.movie.Movie


fun MovieResponseItem.toDomain(isFavorite: Boolean = false): Movie {
    return Movie(
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price,
        category = this.category,
        rating = this.rating,
        year = this.year,
        director = this.director,
        description = this.description,
        isFavorite = isFavorite
    )
}


fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price,
        category = this.category,
        rating = this.rating,
        year = this.year,
        director = this.director,
        description = this.description,
        isFavorite = this.isFavorite
    )
}



fun Movie.toEntity(isFavorite: Boolean = false): MovieEntity {
    return MovieEntity(
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price,
        category = this.category,
        rating = this.rating,
        year = this.year,
        director = this.director,
        description = this.description,
        isFavorite = isFavorite
    )
}