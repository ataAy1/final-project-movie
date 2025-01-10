package com.finalproject.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.usecase.basket.AddItemToBasketUseCase
import com.finalproject.domain.usecase.basket.GetBasketItemsUseCase
import com.finalproject.domain.usecase.basket.RemoveItemFromBasketUseCase
import com.finalproject.domain.usecase.movies.AddToFavoritesUseCase
import com.finalproject.domain.usecase.movies.GetMovieByIdUseCase
import com.finalproject.domain.usecase.movies.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addItemToBasketUseCase: AddItemToBasketUseCase,
    private val removeItemFromBasketUseCase: RemoveItemFromBasketUseCase,
    private val getBasketItemsUseCase: GetBasketItemsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> get() = _state

    private val _basketState = MutableStateFlow(MovieDetailToBasketState())
    val basketState: StateFlow<MovieDetailToBasketState> = _basketState

    private val _favoriteState = MutableStateFlow(MovieDetailToFavoriteState())
    val favoriteState: StateFlow<MovieDetailToFavoriteState> = _favoriteState

    private val _basketItems = MutableStateFlow<List<BasketItem>>(emptyList())
    val basketItems: StateFlow<List<BasketItem>> = _basketItems.asStateFlow()

    init {
        fetchBasketItems("deneme1234d")
    }

     fun fetchBasketItems(userId: String) {
        viewModelScope.launch {
            getBasketItemsUseCase(userId)
                .catch { e ->
                }
                .collect { items ->
                    _basketItems.value = items
                }
        }
    }

    fun getTotalItems(): Int {
        val totalItems = _basketItems.value.size
        return totalItems
    }

    fun getTotalPrice(): Double {
        val totalPrice = _basketItems.value.sumOf { it.price * it.orderAmount.toDouble() }
        return totalPrice
    }


    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            _state.value = MovieDetailState(isLoading = true)

            try {
                val movie = getMovieByIdUseCase.execute(movieId)
                _state.value = MovieDetailState(movie = movie)
            } catch (e: Exception) {
                _state.value = MovieDetailState(error = e.message)
            }
        }
    }

    fun addMovieToBasket(movie: Movie, orderAmount: Int, userName: String) {
        viewModelScope.launch {
            _basketState.value = MovieDetailToBasketState(isLoading = true)

            try {
                getBasketItemsUseCase(userName)
                    .catch { e ->
                        _basketState.value = MovieDetailToBasketState(isLoading = false, errorMessage = e.message)
                    }
                    .collect { basketItems ->
                        val existingItem = basketItems.find { it.name.toString() == movie.name.toString() }

                        if (existingItem != null) {

                            removeItemFromBasketUseCase(existingItem.cartId, userName)
                                .catch { e ->
                                    _basketState.value = MovieDetailToBasketState(isLoading = false, errorMessage = e.message)
                                }
                                .collect { result ->
                                    result.onSuccess {
                                        val updatedItem = existingItem.copy(orderAmount = existingItem.orderAmount + orderAmount)
                                        addItemToBasketUseCase(userName, updatedItem)
                                            .catch { e ->
                                            }
                                            .collect { result ->
                                                result.onSuccess {
                                                    fetchBasketItems(userName)

                                                    _basketState.value = MovieDetailToBasketState(successMessage = "${updatedItem.name}, sepette güncellendi.")
                                                }
                                                result.onFailure {
                                                    _basketState.value = MovieDetailToBasketState(errorMessage = "Failed to update item.")
                                                }
                                            }
                                    }
                                    result.onFailure {
                                        _basketState.value = MovieDetailToBasketState(errorMessage = "Failed to remove item.")
                                    }
                                }
                        } else {

                            val newBasketItem = BasketItem(
                                cartId = movie.id,
                                name = movie.name,
                                image = movie.image,
                                price = movie.price,
                                category = movie.category,
                                rating = movie.rating,
                                year = movie.year,
                                director = movie.director,
                                description = movie.description,
                                orderAmount = orderAmount,
                                userName = userName
                            )

                            addItemToBasketUseCase(userName, newBasketItem)
                                .catch { e ->
                                }
                                .collect { result ->
                                    result.onSuccess {
                                        fetchBasketItems(userName)

                                        _basketState.value = MovieDetailToBasketState(successMessage = "${newBasketItem.name}, sepete eklendi.")
                                    }
                                    result.onFailure {
                                        _basketState.value = MovieDetailToBasketState(errorMessage = "Failed to add item.")
                                    }
                                }
                        }
                    }
            } catch (e: Exception) {
                _basketState.value = MovieDetailToBasketState(isLoading = false, errorMessage = e.message)
            } finally {
                _basketState.value = _basketState.value.copy(isLoading = false)
            }
        }
    }

    fun addMovieToFavorites(movie: Movie) {
        viewModelScope.launch {
            _favoriteState.value = MovieDetailToFavoriteState(isLoading = true)

            try {
                addToFavoritesUseCase(movie)
                _favoriteState.value = MovieDetailToFavoriteState(successMessage = "Eklendi")

                getMovieById(movie.id)

            } catch (e: Exception) {
                _favoriteState.value = MovieDetailToFavoriteState(errorMessage = e.message)
            } finally {
                _favoriteState.value = _favoriteState.value.copy(isLoading = false)
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            _favoriteState.value = MovieDetailToFavoriteState(isLoading = true)

            try {
                removeFromFavoritesUseCase(movieId)
                _favoriteState.value = MovieDetailToFavoriteState(successMessage = "Silindi")

                getMovieById(movieId)

            } catch (e: Exception) {
                _favoriteState.value = MovieDetailToFavoriteState(errorMessage = e.message)
            } finally {
                _favoriteState.value = _favoriteState.value.copy(isLoading = false)
            }
        }
    }

    fun basketResetState() {
        basketState.value.successMessage =null
    }


}