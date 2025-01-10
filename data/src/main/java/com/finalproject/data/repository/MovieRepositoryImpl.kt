package com.finalproject.data.repository

import com.finalproject.data.local.MoviesDao
import com.finalproject.data.mapper.toDomain
import com.finalproject.data.mapper.toEntity
import com.finalproject.data.remote.ApiService
import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import android.util.Log
import retrofit2.HttpException

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesDao: MoviesDao
) : MovieRepository {


    override suspend fun getAllMovies(): Flow<List<Movie>> = flow {
        try {
            val localMovies = moviesDao.getAllMovies().map { it.toDomain() }

            if (localMovies.isNullOrEmpty()) {

                val response = apiService.getMovies()

                if (response.movies.isNullOrEmpty()) {
                    emit(emptyList())
                } else {
                    val movies = response.movies.map { it.toDomain() }

                    moviesDao.insertMovies(movies.map { it.toEntity() })

                    emit(movies)
                }
            } else {

                emit(localMovies)
            }
        } catch (e: Exception) {

            val fallbackMovies = moviesDao.getAllMovies().map { it.toDomain() }
            emit(fallbackMovies)
        }
    }



    override suspend fun getMovieById(movieId: Int): Flow<Movie> = flow {
        try {
            val localMovie = moviesDao.getMovieById(movieId)?.toDomain()

            if (localMovie != null) {
                emit(localMovie)
            } else {

                val response = apiService.getMovies()
                val movie = response.movies.find { it.id == movieId }

                if (movie != null) {
                    emit(movie.toDomain())
                } else {
                    throw Exception("Movie with ID $movieId not found in API")
                }
            }
        } catch (e: Exception) {

            val localMovie = moviesDao.getMovieById(movieId)?.toDomain()

            if (localMovie != null) {
                emit(localMovie)
            } else {
                throw Exception("Movie not found in local database")
            }
        }
    }


    override suspend fun addToFavorites(movie: Movie) {
        val existingMovie = moviesDao.getMovieById(movie.id)

        if (existingMovie != null) {
            val updatedMovie = existingMovie.copy(isFavorite = true)
            moviesDao.insertFavorite(updatedMovie)
        } else {
            val movieEntity = movie.toEntity(isFavorite = true)
            moviesDao.insertFavorite(movieEntity)
        }
    }

    override suspend fun removeFromFavorites(movieId: Int) {
        moviesDao.removeFavorite(movieId)
    }

    override suspend fun getFavoriteMovies(): Flow<List<Movie>> {
        return flow {
            val favoriteMovies = moviesDao.getFavoriteMovies()

            emit(favoriteMovies.map { it.toDomain() })
        }
    }
}