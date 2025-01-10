package com.finalproject.domain.usecase.movies

import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.repository.MovieRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        movieRepository.addToFavorites(movie)
    }
}
