package com.example.testapp.ui.screens.auth.register

sealed class RegistrationEvent {
//    data class ShowSnackbar(val message: String): RegistrationEvent()
//    object HapticError: RegistrationEvent()
    data object NavigateHome : RegistrationEvent()

    data class RegisteredError(val message: String) : RegistrationEvent()
}
