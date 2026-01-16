package com.example.testapp.ui.screens.register

sealed class RegistrationEvent {
//    data class ShowSnackbar(val message: String): RegistrationEvent()
//    object HapticError: RegistrationEvent()
    data class RegisteredSuccess(val message: String): RegistrationEvent()
    data class RegisteredError(val message: String): RegistrationEvent()
}