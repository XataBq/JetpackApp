package com.example.testapp.domain.auth

sealed class EmailValidationResult {
    data object Valid : EmailValidationResult()

    data object Empty : EmailValidationResult()

    data object InvalidFormat : EmailValidationResult()
}

interface EmailValidator {
    fun validate(email: String): EmailValidationResult
}
