package com.example.testapp.data.auth

import android.util.Patterns.EMAIL_ADDRESS
import com.example.testapp.domain.auth.EmailValidationResult
import com.example.testapp.domain.auth.EmailValidator
import javax.inject.Inject

class EmailValidatorImpl
    @Inject
    constructor() : EmailValidator {
        override fun validate(email: String): EmailValidationResult {
            if (email.isEmpty()) return EmailValidationResult.Empty
            val isValid = EMAIL_ADDRESS.matcher(email).matches()
            return if (isValid) EmailValidationResult.Valid else EmailValidationResult.InvalidFormat
        }
    }
