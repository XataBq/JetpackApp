package com.example.testapp.ui.screens.auth.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.ui.models.ValidationState
import com.example.testapp.ui.screens.auth.AuthEvent
import com.example.testapp.ui.screens.auth.AuthViewModel
import com.example.testapp.ui.screens.auth.components.CheckEmailField
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(viewModel: AuthViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthEvent.AuthError -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short,
                        )
                    }
                }

                AuthEvent.NavigateHome -> Unit
                AuthEvent.NavigateLogin -> Unit
                AuthEvent.NavigateRegister -> Unit
                AuthEvent.NavigateForgotPassword -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CheckEmailField(
                    email = uiState.email,
                    formatError = uiState.validationState is ValidationState.Error,
                    onEmailChange = viewModel::onEmailChanged,
                    onClearClicked = viewModel::onClearClicked,
                    onDone = { focusManager.clearFocus() },
                )
            }
        },
    )
}
