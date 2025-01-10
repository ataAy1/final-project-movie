package com.finalproject.domain.usecase.movies

import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(movieId: Int): Flow<Movie> {
        return movieRepository.getMovieById(movieId)
    }
}
