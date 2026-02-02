package com.example.testapp.domain.repository

interface AuthRepository {
    suspend fun register(email: String): Result<Unit>
    suspend fun login(email: String): Result<Unit>
}
