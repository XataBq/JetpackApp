package com.example.testapp.data.auth

import com.example.testapp.di.IoDispatcher
import com.example.testapp.domain.auth.AuthRepository
import com.example.testapp.domain.auth.AuthResult
import com.example.testapp.domain.auth.EmailValidationResult
import com.example.testapp.domain.auth.EmailValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val emailValidator: EmailValidator,
        @IoDispatcher private val io: CoroutineDispatcher,
    ) : AuthRepository {
        override suspend fun register(email: String): AuthResult =
            withContext(io) {
                when (emailValidator.validate(email)) {
                    EmailValidationResult.Valid -> AuthResult.Success
                    else -> return@withContext AuthResult.Error.InvalidEmail
                }

                runCatching {
                    // TODO: network later
                    Unit
                }.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { it.toAuthError() },
                )
            }

        override suspend fun login(email: String): AuthResult =
            withContext(io) {
                when (emailValidator.validate(email)) {
                    EmailValidationResult.Valid -> AuthResult.Success
                    else -> return@withContext AuthResult.Error.InvalidEmail
                }

                runCatching { Unit }.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { it.toAuthError() },
                )
            }

        private fun Throwable.toAuthError(): AuthResult.Error =
            when (this) {
                is IOException -> AuthResult.Error.Network
                else -> AuthResult.Error.Unknown(this)
            }
    }
