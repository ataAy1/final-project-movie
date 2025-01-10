package com.finalproject.domain.usecase.auth


import com.finalproject.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute() {
        authRepository.signOut()
    }
}
