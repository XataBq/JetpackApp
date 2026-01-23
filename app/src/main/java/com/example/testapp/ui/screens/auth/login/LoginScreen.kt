package com.example.testapp.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.ui.components.ValidationBlock
import com.example.testapp.ui.models.ValidationState
import com.example.testapp.ui.screens.auth.AuthEvent
import com.example.testapp.ui.screens.auth.AuthViewModel
import com.example.testapp.ui.screens.auth.components.CheckEmailField
import com.example.testapp.ui.screens.auth.components.PrimaryButton
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onRegisterScreen: () -> Unit,
    onNavigateHome: (String) -> Unit,
) {
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
                AuthEvent.NavigateHome -> onNavigateHome(uiState.email)
                AuthEvent.NavigateLogin -> Unit
                AuthEvent.NavigateRegister -> Unit
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
                verticalArrangement = Arrangement.Center,
            ) {
                CheckEmailField(
                    email = uiState.email,
                    formatError = uiState.validationState is ValidationState.Error,
                    onEmailChange = viewModel::onEmailChanged,
                    onClearClicked = viewModel::onClearClicked,
                    onDone = { -> focusManager.clearFocus() },
                )

                PrimaryButton(
                    text = "LogIn",
                    onButtonClick = {
                        focusManager.clearFocus()
                        viewModel.onLoginClick()
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                PrimaryButton(
                    text = "Registration",
                    onButtonClick = {
                        focusManager.clearFocus()
                        onRegisterScreen()
                    },
                )
                ValidationBlock(uiState.validationState)
            }
        },
    )
}
