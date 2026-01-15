package com.example.testapp.ui.screens.register

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.ViewModel
import com.example.testapp.ui.models.ValidationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    val uiState = _uiState.asStateFlow()
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

        _uiState.update {
            it.copy(
                validationState = if (email.isEmpty() || !isValid) {
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
            )
        }
    }
}