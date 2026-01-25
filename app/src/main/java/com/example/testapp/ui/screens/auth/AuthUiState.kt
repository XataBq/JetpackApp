package com.example.testapp.ui.screens.auth

import com.example.testapp.ui.models.ValidationState

data class AuthUiState(
    val email: String = "",
//    val isEmailValid: Boolean = false,
    val validationState: ValidationState = ValidationState.None,
)
