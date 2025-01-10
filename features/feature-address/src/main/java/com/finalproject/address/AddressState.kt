package com.finalproject.address

import com.finalproject.domain.model.user.AddressModel

sealed class AddressState {
    object Idle : AddressState()
    object Loading : AddressState()
    data class Saving(val isSaving: Boolean, val message: String) : AddressState()
    data class Success(val addresses: List<AddressModel>) : AddressState()
    data class Error(val message: String) : AddressState()
}
