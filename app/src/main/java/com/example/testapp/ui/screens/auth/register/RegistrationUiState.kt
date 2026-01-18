package com.example.testapp.ui.screens.auth.register

import com.example.testapp.ui.models.ValidationState

data class RegistrationUiState(
    val email: String = "",
    val validationState: ValidationState = ValidationState.None,
)
