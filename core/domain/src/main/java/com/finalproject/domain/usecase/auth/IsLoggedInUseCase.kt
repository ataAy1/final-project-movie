package com.finalproject.domain.usecase.auth


import com.finalproject.domain.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(): Boolean {
        return authRepository.isLoggedIn()
    }
}
