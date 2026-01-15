package com.example.testapp.ui.models

sealed class ValidationState {
    data object None: ValidationState()
    data class Error(val message: String): ValidationState()
    data class Success(val message: String): ValidationState()
}