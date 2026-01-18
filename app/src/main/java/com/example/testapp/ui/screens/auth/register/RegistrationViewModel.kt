package com.example.testapp.ui.screens.auth.register

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

class RegistrationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    private val _events = MutableSharedFlow<RegistrationEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiState = _uiState.asStateFlow()
    val events = _events.asSharedFlow()
    val testEmail: String = "example@king.ru"

    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun onClearClicked() {
        _uiState.update {
            it.copy(email = "", validationState = ValidationState.None)
        }
    }

    fun onRegistryClick() {
        val email = _uiState.value.email
        val isValid = EMAIL_ADDRESS.matcher(email).matches()

        val newValidation = if (email.isEmpty() || !isValid) {
            ValidationState.Error(
                "Некорректный Email адрес. Проверьте правильность введенных данных"
            )
        } else if (email == testEmail) {
            ValidationState.Error(
                "Аккаунт с такой почтой уже зарегистрирован.\n" +
                        "Проверьте правильность введенных данных"
            )

        } else {
            ValidationState.Success("Аккаунт успешно зарегистрирован!")
        }

        _uiState.update { it.copy(validationState = newValidation) }

        viewModelScope.launch {
            when(newValidation) {
                is ValidationState.Error -> {
                    _events.tryEmit(RegistrationEvent.RegisteredError(newValidation.message))
                }
                is ValidationState.Success -> {
                    _events.emit(RegistrationEvent.NavigateHome)
                }
                ValidationState.None -> Unit
            }
        }
    }
}