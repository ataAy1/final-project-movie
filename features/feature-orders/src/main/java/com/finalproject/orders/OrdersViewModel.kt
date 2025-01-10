package com.finalproject.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.usecase.order.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _ordersState = MutableStateFlow<OrdersState>(OrdersState.Idle)
    val ordersState: StateFlow<OrdersState> = _ordersState

    fun fetchOrders() {
        viewModelScope.launch {
            _ordersState.value = OrdersState.Loading
            try {
                getOrdersUseCase.execute().collect { orders ->
                    if (orders.isEmpty()) {
                        _ordersState.value = OrdersState.Empty
                    } else {
                        _ordersState.value = OrdersState.Success(orders)
                    }
                }
            } catch (e: Exception) {
                _ordersState.value = OrdersState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
