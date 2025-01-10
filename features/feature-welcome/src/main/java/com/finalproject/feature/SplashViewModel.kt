package com.finalproject.feature


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.usecase.auth.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _isLoginUser = MutableStateFlow(false)
    val isLoginUser: StateFlow<Boolean> = _isLoginUser

    init {
        viewModelScope.launch {
            _isLoginUser.value = isLoggedInUseCase.execute()
        }
    }
}
