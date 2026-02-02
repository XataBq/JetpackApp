package com.example.testapp.data.repository

import com.example.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun register(email: String): Result<Unit> {
        //TODO("Сеть, база")
        return Result.success(Unit)
    }

    override suspend fun login(email: String): Result<Unit> {
        //TODO("Сеть, база")
        return Result.success(Unit)
    }
}
