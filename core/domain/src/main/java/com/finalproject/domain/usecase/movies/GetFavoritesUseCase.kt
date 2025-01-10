package com.finalproject.domain.usecase.movies

import com.finalproject.domain.repository.MovieRepository
import com.finalproject.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovies()
    }
}
