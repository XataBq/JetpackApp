package com.example.testapp.domain.auth

interface AuthRepository {
    suspend fun register(email: String): AuthResult

    suspend fun login(email: String): AuthResult
}
