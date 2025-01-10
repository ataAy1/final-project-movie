package com.finalproject.domain.usecase.home

import com.finalproject.domain.repository.MovieRepository
import com.finalproject.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> = movieRepository.getAllMovies()
}
