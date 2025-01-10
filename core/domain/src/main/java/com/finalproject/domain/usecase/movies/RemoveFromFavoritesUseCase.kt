package com.finalproject.domain.usecase.movies

import com.finalproject.domain.repository.MovieRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        movieRepository.removeFromFavorites(movieId)
    }
}
