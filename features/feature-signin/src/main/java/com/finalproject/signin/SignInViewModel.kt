package com.finalproject.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.usecase.auth.SignInUseCase
import com.finalproject.domain.usecase.auth.IsLoggedInUseCase
import com.finalproject.signin.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                signInUseCase.signIn(email, password)
                _signInState.value = SignInState.Success("Başarili")
            } catch (e: Exception) {
                _signInState.value = SignInState.Error("Hata: ${e.message}")
            }
        }
    }


}
