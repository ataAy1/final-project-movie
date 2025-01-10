package com.finalproject.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.usecase.home.GetMoviesUseCase
import com.finalproject.domain.usecase.movies.AddToFavoritesUseCase
import com.finalproject.domain.usecase.movies.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    fun getMovies() {
        _state.value = HomeState(isLoading = true)

        viewModelScope.launch {
            try {
                getMoviesUseCase().collect { movies ->
                    _state.value = HomeState(movies = movies)
                }
            } catch (e: Exception) {
                _state.value = HomeState(error = e.message)
            }
        }
    }

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            try {
                addToFavoritesUseCase(movie)
                _state.value = _state.value.copy(
                    movies = _state.value.movies?.map {
                        if (it.id == movie.id) it.copy(isFavorite = true) else it
                    }
                )
            } catch (e: Exception) {
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            try {
                removeFromFavoritesUseCase(movieId)
                _state.value = _state.value.copy(
                    movies = _state.value.movies?.map {
                        if (it.id == movieId) it.copy(isFavorite = false) else it
                    }
                )
            } catch (e: Exception) {
            }
        }
    }

    fun syncFavorites(favorites: List<Movie>) {
        _state.value = _state.value.copy(
            movies = _state.value.movies?.map { movie ->
                movie.copy(isFavorite = favorites.any { it.id == movie.id })
            }
        )
    }
}
