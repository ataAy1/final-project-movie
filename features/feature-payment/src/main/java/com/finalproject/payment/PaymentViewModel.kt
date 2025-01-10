package com.finalproject.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.payment.CouponModel
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.domain.model.user.UserSettings
import com.finalproject.domain.usecase.basket.RemoveAllFromBasketUseCase
import com.finalproject.domain.usecase.coupon.SaveCouponUseCase
import com.finalproject.domain.usecase.discount_coupon.GetDiscountCouponsUseCase
import com.finalproject.domain.usecase.discount_coupon.UpdateDiscountCouponUseCase
import com.finalproject.domain.usecase.order.AddOrderUseCase
import com.finalproject.domain.usecase.profile.address.GetAddressUseCase
import com.finalproject.domain.usecase.profile.cards.GetCardsUseCase
import com.finalproject.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val addOrderUseCase: AddOrderUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val saveCouponUseCase: SaveCouponUseCase,
    private val removeAllFromBasketUseCase: RemoveAllFromBasketUseCase,
    private val getDiscountCouponsUseCase: GetDiscountCouponsUseCase,
    private val updateDiscountCouponUseCase: UpdateDiscountCouponUseCase,

    ) : ViewModel() {

    private val _addresses = MutableStateFlow<List<AddressModel>>(emptyList())
    val addresses: StateFlow<List<AddressModel>> = _addresses

    private val _cards = MutableStateFlow<List<CardsModel>>(emptyList())
    val cards: StateFlow<List<CardsModel>> = _cards

    private val _state = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val state: StateFlow<PaymentState> = _state

    private val _updatedOrderModelList = MutableStateFlow<List<OrderModel>>(emptyList())
    val updatedOrderModelList: StateFlow<List<OrderModel>> = _updatedOrderModelList

    private val _userSettings = MutableStateFlow<UserSettings?>(null)
    val userSettings: StateFlow<UserSettings?> = _userSettings

    private val _removeBasketResult = MutableStateFlow<Result<Unit>?>(null)
    val removeBasketResult: StateFlow<Result<Unit>?> = _removeBasketResult

    private val _discountCouponState = MutableStateFlow(DiscountCouponState())
    val discountCouponState: StateFlow<DiscountCouponState> = _discountCouponState



    init {
        fetchAddresses()
        fetchCards()
        fetchUserSettings()
        getDiscountCoupons()
    }


    private fun fetchAddresses() {
        viewModelScope.launch {
            try {
                val addressList = getAddressUseCase()
                _addresses.value = addressList
            } catch (e: Exception) {
            }
        }
    }

    private fun fetchCards() {
        viewModelScope.launch {
            try {
                val cardList = getCardsUseCase()
                _cards.value = cardList
            } catch (e: Exception) {
            }
        }
    }

    private fun fetchUserSettings() {
        viewModelScope.launch {
            try {
                _userSettings.value = getUserSettingsUseCase()
            } catch (e: Exception) {
            }
        }
    }

    fun saveOrders(orderList: List<OrderModel>) {
        viewModelScope.launch {
            _state.value = PaymentState.Saving
            try {
                orderList.forEach { order ->
                    addOrderUseCase(order).collect { result ->
                        result.fold(
                            onSuccess = {
                                removeAllFromBasketUseCase("deneme1234d", listOf(order.movieID.toString())).collect { removeResult ->
                                    removeResult.fold(
                                        onSuccess = {
                                        },
                                        onFailure = { throw it }
                                    )
                                }
                            },
                            onFailure = { throw it }
                        )
                    }
                }
                _state.value = PaymentState.Success("Başarili !")
            } catch (e: Exception) {
                _state.value = PaymentState.Error("Hata: ${e.message}")
            }
        }
    }



    fun getDiscountCoupons() {
        viewModelScope.launch {
            _discountCouponState.value = DiscountCouponState(isLoading = true)
            try {
                val discountCoupons = getDiscountCouponsUseCase.execute()
                _discountCouponState.value = DiscountCouponState(discountCoupons = discountCoupons)
            } catch (e: Exception) {
                _discountCouponState.value = DiscountCouponState(error = e.message)
            }
        }
    }

    fun saveCoupon(coupon: CouponModel) {
        viewModelScope.launch {
            saveCouponUseCase.execute(coupon)
        }
    }



    fun markCouponAsInactive(couponId: String) {
        viewModelScope.launch {
                updateDiscountCouponUseCase.execute(couponId)
        }
    }
}
