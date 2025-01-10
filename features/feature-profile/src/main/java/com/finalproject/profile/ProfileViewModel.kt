package com.finalproject.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.finalproject.domain.model.user.UserSettings
import com.finalproject.domain.usecase.auth.SignOutUseCase
import com.finalproject.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val signOutUseCase: SignOutUseCase

) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun getUserSettings() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val userSettings = getUserSettingsUseCase()

                _state.value = _state.value.copy(
                    userSettings = userSettings,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun signOut(navController: NavController) {
        viewModelScope.launch {
            try {
                signOutUseCase.execute()
                navController.navigate("welcome_screen")
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

}
