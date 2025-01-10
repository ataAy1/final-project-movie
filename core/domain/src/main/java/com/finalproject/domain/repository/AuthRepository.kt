package com.finalproject.domain.repository


interface AuthRepository {
    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    fun isLoggedIn(): Boolean
    fun signOut()
}
