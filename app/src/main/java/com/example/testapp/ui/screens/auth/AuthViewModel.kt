package com.example.testapp.ui.screens.auth

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.auth.AuthRepository
import com.example.testapp.domain.auth.AuthResult
import com.example.testapp.domain.auth.EmailValidationResult
import com.example.testapp.domain.auth.EmailValidator
import com.example.testapp.ui.models.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val validator: EmailValidator,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AuthUiState())
        private val _events =
            MutableSharedFlow<AuthEvent>(
                replay = 0,
                extraBufferCapacity = 1,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )
        val uiState = _uiState.asStateFlow()
        val events = _events.asSharedFlow()
        val testEmail: String = "1@mail.ru"

        fun setFieldFormatErrorFalse() {
            _uiState.update { it.copy(fieldFormatError = false) }
        }

        fun onEmailChanged(newEmail: String) {
            val result = validator.validate(newEmail)
            val isValid = when(result) {
                EmailValidationResult.Valid -> true
                EmailValidationResult.Empty -> null
                EmailValidationResult.InvalidFormat -> false
            }
            _uiState.update {
                it.copy(
                    email = newEmail,
                    fieldFormatError = false,
                    isEmailValid = isValid
                )
            }
        }

        fun onClearClicked() {
            _uiState.update {
                it.copy(
                    email = "",
                    fieldFormatError = false,
                    isEmailValid = null,
                )
            }
        }

        fun onRegistryClick() {
            val email = uiState.value.email
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                when (authRepository.register(email)) {
                    AuthResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, fieldFormatError = false) }
                        _events.emit(AuthEvent.NavigateHome)
                    }

                    AuthResult.Error.InvalidEmail -> {
                        _uiState.update { it.copy(isLoading = false, fieldFormatError = true) }
                        _events.tryEmit(AuthEvent.AuthError("Invalid email"))
                    }

                    AuthResult.Error.Network -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.tryEmit(AuthEvent.AuthError("NetworkProblems. Check your Internet connection"))
                    }
                    else -> {
                        _uiState.update {it.copy(isLoading = false)}
                        _events.tryEmit(AuthEvent.AuthError("Unknown error. Try again, please!)"))
                    }
                }
            }
        }

//        fun onRegistryClick() {
//            val email = uiState.value.email
//            val isValid = EMAIL_ADDRESS.matcher(email).matches()
//            viewModelScope.launch {
//                authRepository.register(uiState.value.email)
//            }
//
//            val newValidation =
//                if (email.isEmpty() || !isValid) {
//                    ValidationState.Error(
//                        "Некорректный Email адрес. Проверьте правильность введенных данных",
//                    )
//                } else if (email == testEmail) {
//                    ValidationState.Error(
//                        "Аккаунт с такой почтой уже зарегистрирован.\n" +
//                            "Проверьте правильность введенных данных",
//                    )
//                } else {
//                    ValidationState.Success
//                }
//
//            _uiState.update { it.copy(validationState = newValidation) }
//
//            viewModelScope.launch {
//                when (newValidation) {
//                    is ValidationState.Error -> {
//                        _events.tryEmit(AuthEvent.AuthError(newValidation.message))
//                    }
//
//                    is ValidationState.Success -> {
//                        authRepository.register("2@aai.r")
//                        _events.emit(AuthEvent.NavigateHome)
//                    }
//
//                    ValidationState.None -> Unit
//                }
//            }
//        }

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
                        _uiState.update { it.copy(isLoading = false, fieldFormatError = true) }
                        _events.tryEmit(AuthEvent.AuthError(newValidation.message))
                    }
                    is ValidationState.Success -> {
                        _uiState.update { it.copy(isLoading = false, fieldFormatError = false) }
                        _events.emit(AuthEvent.NavigateHome)
                    }
                    ValidationState.None -> Unit
                }
            }
        }
    }
