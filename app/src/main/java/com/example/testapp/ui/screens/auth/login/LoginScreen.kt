package com.example.testapp.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.testapp.R
import com.example.testapp.ui.screens.auth.AuthEvent
import com.example.testapp.ui.screens.auth.AuthViewModel
import com.example.testapp.ui.screens.auth.components.CheckEmailField
import com.example.testapp.ui.screens.auth.components.NavButton
import com.example.testapp.ui.screens.auth.components.PrimaryButton
import com.example.testapp.ui.screens.home.StudyAppHeader
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterScreen: () -> Unit,
    onForgotPasswordScreen: () -> Unit,
    onNavigateHome: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val canSubmit = (uiState.isEmailValid == true) && !uiState.isLoading

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
                Spacer(modifier = Modifier.height(70.dp))
                StudyAppHeader(
                    title = "Авторизация",
                    subtitle = "Введите почту и пароль",
                )
                Spacer(modifier = Modifier.height(200.dp))
                CheckEmailField(
                    email = uiState.email,
                    formatError = uiState.fieldFormatError,
                    onEmailChange = viewModel::onEmailChanged,
                    onClearClicked = viewModel::onClearClicked,
                    onDone = { focusManager.clearFocus() },
                )
                PrimaryButton(
                    text = stringResource(R.string.log_in),
                    onButtonClick = {
                        focusManager.clearFocus()
                        viewModel.onLoginClick()
                    },
                    enabled = canSubmit,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    val modifier: Modifier = Modifier.weight(0.45F).height(30.dp)
                    NavButton(
                        modifier = modifier,
                        text = "Registration",
                        onButtonClick = {
                            focusManager.clearFocus()
                            onRegisterScreen()
                        },
                    )
                    Spacer(modifier = Modifier.weight(0.1F))
                    NavButton(
                        modifier = modifier,
                        text = "Forgot password",
                        onButtonClick = {
                            focusManager.clearFocus()
                            onForgotPasswordScreen()
                        },
                    )
                }
            }
        },
    )
}
