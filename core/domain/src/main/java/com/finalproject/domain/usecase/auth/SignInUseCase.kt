package com.finalproject.domain.usecase.auth

import com.finalproject.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun signIn(email: String, password: String) {
        authRepository.signIn(email, password)
    }
}
