package com.finalproject.coupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.usecase.coupon.GetCouponsUseCase
import com.finalproject.domain.usecase.discount_coupon.GetDiscountCouponsUseCase
import com.finalproject.domain.usecase.discount_coupon.SaveDiscountCouponUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val getCouponsUseCase: GetCouponsUseCase,
    private val getDiscountCouponsUseCase: GetDiscountCouponsUseCase,
    private val saveDiscountCouponUseCase: SaveDiscountCouponUseCase,

    ) : ViewModel() {

    private val _couponState = MutableStateFlow(CouponState())
    val couponState: StateFlow<CouponState> = _couponState


    fun getCoupons() {
        viewModelScope.launch {
            _couponState.value = CouponState(isLoading = true)

            try {
                val coupons = getCouponsUseCase.execute()
                _couponState.value = CouponState(coupons = coupons)
            } catch (e: Exception) {
                _couponState.value = CouponState(error = e.message)
            }
        }
    }
}