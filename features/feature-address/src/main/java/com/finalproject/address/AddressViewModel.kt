package com.finalproject.address

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.domain.usecase.profile.address.GetAddressUseCase
import com.finalproject.domain.usecase.profile.address.SaveAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val saveAddressUseCase: SaveAddressUseCase
) : ViewModel() {

    private val _getAddressState = MutableStateFlow<AddressState>(AddressState.Idle)
    private val _saveAddressState = MutableStateFlow<AddressState>(AddressState.Idle)

    val getAddressState: StateFlow<AddressState> = _getAddressState
    val saveAddressState: StateFlow<AddressState> = _saveAddressState

    fun getAddresses() {
        if (_getAddressState.value is AddressState.Loading) return

        _getAddressState.value = AddressState.Loading
        viewModelScope.launch {
            try {
                val addresses = getAddressUseCase()
                _getAddressState.value = AddressState.Success(addresses)
            } catch (e: Exception) {
                _getAddressState.value = AddressState.Error(e.message ?: "Hata")
            }
        }
    }

    fun saveAddress(address: AddressModel) {
        if (_saveAddressState.value is AddressState.Saving) return

        _saveAddressState.value = AddressState.Saving(true, "Adres Alınıyor...")
        viewModelScope.launch {
            try {
                saveAddressUseCase(address)
                _saveAddressState.value = AddressState.Success(emptyList())
            } catch (e: Exception) {
                _saveAddressState.value = AddressState.Error("Hata: ${e.message}")
            }
        }
    }
}
