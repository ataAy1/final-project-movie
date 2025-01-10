// SignUpUseCase.kt
package com.finalproject.domain.usecase.auth

import com.finalproject.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun signUp(email: String, password: String): Result<Boolean> {
        return try {
            authRepository.signUp(email, password)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
