package com.finalproject.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading
            try {
                val result = signUpUseCase.signUp(email, password)
                if (result.isSuccess) {
                    _signUpState.value = SignUpState.Success("Başarili Kayıt")
                } else {
                    _signUpState.value = SignUpState.Error("Hata: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error("Hata ${e.message}")
            }
        }
    }
}
