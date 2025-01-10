package com.finalproject.settings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.domain.model.user.UserSettings
import com.finalproject.domain.usecase.settings.SaveUserSettingsUseCase
import com.finalproject.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> get() = _state

    init {
        getUserSettings()
    }

    fun getUserSettings() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val userSettings = getUserSettingsUseCase()
                _state.value = _state.value.copy(userSettings = userSettings, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }


    fun saveUserSettings(
        username: String,
        userPhone: String,
        userMail: String,
        profilePhotoUri: Uri?
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true)
            try {
                saveUserSettingsUseCase(
                    UserSettings(username, userPhone, userMail, profilePhotoUri.toString())
                )
                _state.value = _state.value.copy(isSaving = false, isSaveSuccessful = true)
                getUserSettings()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isSaving = false
                )
            }
        }
    }
}
