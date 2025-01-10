package com.finalproject.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.domain.usecase.basket.AddItemToBasketUseCase
import com.finalproject.domain.usecase.basket.GetBasketItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.finalproject.domain.usecase.basket.RemoveItemFromBasketUseCase

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketItemsUseCase: GetBasketItemsUseCase,
    private val addItemToBasketUseCase: AddItemToBasketUseCase,
    private val removeItemFromBasketUseCase: RemoveItemFromBasketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BasketState>(BasketState.Idle)
    val uiState: StateFlow<BasketState> get() = _uiState

    fun getBasketItems(userName: String) {
        viewModelScope.launch {

            getBasketItemsUseCase(userName)
                .catch { exception ->
                    _uiState.value = BasketState.Error(exception.message ?: "Hata")
                }
                .collect { items ->
                    if (items.isNotEmpty()) {
                        _uiState.value = BasketState.Success(items)
                    } else {
                        _uiState.value = BasketState.Error("")
                    }
                }
        }
    }



    fun addOrUpdateBasketItem(userName: String, basketItem: BasketItem) {
        viewModelScope.launch {
            val existingItem = (_uiState.value as? BasketState.Success)?.items?.find { it.cartId == basketItem.cartId }

            if (existingItem != null) {
                removeItemFromBasketUseCase(existingItem.cartId, userName).collect { result ->
                    result.onSuccess {
                    }.onFailure {
                    }
                }
                addItemToBasketUseCase(userName, basketItem).collect { result ->
                    result.onSuccess {
                    }.onFailure {
                    }
                }
            } else {
                addItemToBasketUseCase(userName, basketItem).collect { result ->
                    result.onSuccess {
                    }.onFailure {
                    }
                }
            }
            getBasketItems(userName)
        }
    }

    fun removeBasketItem(cartId: Int, userName: String) {
        viewModelScope.launch {
            _uiState.value = BasketState.Idle

            removeItemFromBasketUseCase(cartId, userName)
                .catch { e ->
                    _uiState.value = BasketState.Error("Error removing item.")
                }
                .collect { result ->
                    result.onSuccess {
                        getBasketItems(userName)
                    }
                        .onFailure {
                            _uiState.value = BasketState.Error("Failed to remove item.")
                        }
                }
        }
    }
}