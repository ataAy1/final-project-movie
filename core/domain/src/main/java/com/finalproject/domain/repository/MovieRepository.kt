package com.finalproject.domain.repository

import com.finalproject.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieById(movieId: Int): Flow<Movie>
    suspend fun getAllMovies(): Flow<List<Movie>>

    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movieId: Int)

    suspend fun getFavoriteMovies(): Flow<List<Movie>>

}
