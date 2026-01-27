package com.example.testapp.ui.screens.auth

sealed class AuthEvent {
    data object NavigateHome : AuthEvent()

    data object NavigateLogin : AuthEvent()

    data object NavigateRegister : AuthEvent()

    data object NavigateForgotPassword : AuthEvent()

    data class AuthError(
        val message: String,
    ) : AuthEvent()
}
