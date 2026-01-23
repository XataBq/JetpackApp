package com.example.testapp.ui.screens.auth

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.ui.models.ValidationState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState())
    private val _events =
        MutableSharedFlow<AuthEvent>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val uiState = _uiState.asStateFlow()
    val events = _events.asSharedFlow()
    val testEmail: String = "1@mail.ru"

    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                validationState = ValidationState.None,
            )
        }
    }

    fun onClearClicked() {
        _uiState.update {
            it.copy(email = "", validationState = ValidationState.None)
        }
    }

    fun onRegistryClick() {
        val email = uiState.value.email
        val isValid = EMAIL_ADDRESS.matcher(email).matches()

        val newValidation =
            if (email.isEmpty() || !isValid) {
                ValidationState.Error(
                    "Некорректный Email адрес. Проверьте правильность введенных данных",
                )
            } else if (email == testEmail) {
                ValidationState.Error(
                    "Аккаунт с такой почтой уже зарегистрирован.\n" +
                        "Проверьте правильность введенных данных",
                )
            } else {
                ValidationState.Success
            }

        _uiState.update { it.copy(validationState = newValidation) }

        viewModelScope.launch {
            when (newValidation) {
                is ValidationState.Error -> {
                    _events.tryEmit(AuthEvent.AuthError(newValidation.message))
                }

                is ValidationState.Success -> {
                    _events.emit(AuthEvent.NavigateHome)
                }

                ValidationState.None -> Unit
            }
        }
    }

    fun onLoginClick() {
        val email = uiState.value.email

        val newValidation =
            if (email == testEmail) {
                ValidationState.Success
            } else {
                ValidationState.Error(
                    "Некорректный Email адрес. Проверьте правильность введенных данных",
                )
            }

        _uiState.update { it.copy(validationState = newValidation) }

        viewModelScope.launch {
            when (newValidation) {
                is ValidationState.Error -> {
                    _events.tryEmit(AuthEvent.AuthError(newValidation.message))
                }
                is ValidationState.Success -> {
                    _events.emit(AuthEvent.NavigateHome)
                }
                ValidationState.None -> Unit
            }
        }
    }
}
