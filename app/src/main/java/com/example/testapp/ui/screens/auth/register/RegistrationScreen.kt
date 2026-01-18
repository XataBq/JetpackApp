package com.example.testapp.ui.screens.auth.register

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.ui.components.ValidationBlock
import com.example.testapp.ui.screens.auth.AuthViewModel
import com.example.testapp.ui.screens.auth.components.CheckEmailField
import com.example.testapp.ui.screens.auth.components.PrimaryButton
import com.example.testapp.ui.screens.home.StudyAppHeader
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onNavigateHome: (String) -> Unit,
    onNavigateLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isEmailFormatValid: Boolean by remember {
        derivedStateOf {
            if (uiState.email.isNotEmpty())
                EMAIL_ADDRESS.matcher(uiState.email).matches()
            else
                true
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RegistrationEvent.RegisteredError -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                RegistrationEvent.NavigateHome -> onNavigateHome(uiState.email)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            StudyAppHeader(
                title = "Регистрация",
                subtitle = "Введите почту"
            )
            Spacer(modifier = Modifier.height(200.dp))
            CheckEmailField(
                email = uiState.email,
                isEmailValid = isEmailFormatValid,
                onEmailChange = viewModel::onEmailChanged,
                onClearClicked = viewModel::onClearClicked,
                onDone = { focusManager.clearFocus() }
            )
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = "Register",
                onButtonClick = viewModel::onRegistryClick,
            )
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = "Login",
                onButtonClick = {
                    onNavigateLogin()
                },
            )
            ValidationBlock(uiState.validationState)
        }
    }
}
