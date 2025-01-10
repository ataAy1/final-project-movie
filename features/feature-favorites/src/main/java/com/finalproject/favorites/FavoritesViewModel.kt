package com.finalproject.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.usecase.movies.GetFavoritesUseCase
import com.finalproject.domain.model.movie.Movie
import com.finalproject.domain.usecase.basket.AddItemToBasketUseCase
import com.finalproject.domain.usecase.basket.GetBasketItemsUseCase
import com.finalproject.domain.usecase.basket.RemoveItemFromBasketUseCase
import com.finalproject.domain.usecase.movies.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val addItemToBasketUseCase: AddItemToBasketUseCase,
    private val removeItemFromBasketUseCase: RemoveItemFromBasketUseCase,
    private val getBasketItemsUseCase: GetBasketItemsUseCase,

    ) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state

    private val _basketState = MutableStateFlow(FavoritesToBasketState())
    val basketState: StateFlow<FavoritesToBasketState> = _basketState

    fun getFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { favorites ->
                _state.value = FavoritesState(favoriteMovies = favorites)
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            try {
                removeFromFavoritesUseCase(movieId)
                getFavorites()

            } catch (e: Exception) {
            }
        }
    }

    fun addMovieToBasket(movie: Movie, orderAmount: Int, userName: String) {
        viewModelScope.launch {
            _basketState.value = FavoritesToBasketState(isLoading = true)

            try {
                getBasketItemsUseCase(userName)
                    .catch { e ->
                        _basketState.value = FavoritesToBasketState(isLoading = false, errorMessage = e.message)
                    }
                    .collect { basketItems ->
                        val existingItem = basketItems.find { it.name.toString() == movie.name.toString() }

                        if (existingItem != null) {

                            removeItemFromBasketUseCase(existingItem.cartId, userName)
                                .catch { e ->
                                    _basketState.value = FavoritesToBasketState(isLoading = false, errorMessage = e.message)
                                }
                                .collect { result ->
                                    result.onSuccess {
                                        val updatedItem = existingItem.copy(orderAmount = existingItem.orderAmount + orderAmount)
                                        addItemToBasketUseCase(userName, updatedItem)
                                            .catch { e ->
                                            }
                                            .collect { result ->
                                                result.onSuccess {
                                                    _basketState.value = FavoritesToBasketState(successMessage = "${updatedItem.name}, sepette güncellendi.")
                                                    removeFromFavorites(movie.id)
                                                }
                                                result.onFailure {
                                                    _basketState.value = FavoritesToBasketState(errorMessage = "Failed to update item.")
                                                }
                                            }
                                    }
                                    result.onFailure {
                                        _basketState.value = FavoritesToBasketState(errorMessage = "Failed to remove item.")
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
                                        removeFromFavorites(movie.id)
                                        _basketState.value = FavoritesToBasketState(successMessage = "${newBasketItem.name}, sepete eklendi.")
                                    }
                                    result.onFailure {
                                        _basketState.value = FavoritesToBasketState(errorMessage = "Failed to add item.")
                                    }
                                }
                        }
                    }
            } catch (e: Exception) {
                _basketState.value = FavoritesToBasketState(isLoading = false, errorMessage = e.message)
            } finally {
                _basketState.value = _basketState.value.copy(isLoading = false)
            }
        }
    }
}
