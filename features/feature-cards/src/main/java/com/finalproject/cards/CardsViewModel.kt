package com.finalproject.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.domain.usecase.profile.cards.GetCardsUseCase
import com.finalproject.domain.usecase.profile.cards.SaveCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val saveCardUseCase: SaveCardUseCase
) : ViewModel() {

    private val _cardsState = MutableStateFlow<CardsState>(CardsState.Loading)
    val cardsState: StateFlow<CardsState> get() = _cardsState


    private val _savingState = MutableStateFlow<CardsState>(CardsState.Loading)
    val savingState: StateFlow<CardsState> get() = _savingState


    fun fetchCards() {
        _cardsState.value = CardsState.Loading
        viewModelScope.launch {
            try {
                val cards = getCardsUseCase()
                _cardsState.value = CardsState.Success(cards)
            } catch (e: Exception) {
                _cardsState.value = CardsState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun saveCard(card: CardsModel) {
        _savingState.value = CardsState.Saving
        viewModelScope.launch {
            try {
                saveCardUseCase(card)

                _savingState.value = CardsState.AddSuccess(true)

                fetchCards()
            } catch (e: Exception) {
                _savingState.value = CardsState.Error(e.message ?: "An error occurred")
            }
        }
    }
    fun resetState() {
        _savingState.value = CardsState.Loading
    }
}
