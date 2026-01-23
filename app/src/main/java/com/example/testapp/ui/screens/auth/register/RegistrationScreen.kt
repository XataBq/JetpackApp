package com.example.testapp.ui.screens.auth.register

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.testapp.ui.screens.home.StudyAppHeader
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    onNavigateHome: (String) -> Unit,
    onNavigateLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

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
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { focusManager.clearFocus() })
                    },
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            StudyAppHeader(
                title = "Регистрация",
                subtitle = "Введите почту",
            )
            Spacer(modifier = Modifier.height(200.dp))
            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp)
                        .height(24.dp),
            ) {
                Text(text = "asasasasa")
            }
            CheckEmailField(
                email = uiState.email,
                formatError = uiState.validationState is ValidationState.Error,
                onEmailChange = viewModel::onEmailChanged,
                onClearClicked = viewModel::onClearClicked,
                onDone = { focusManager.clearFocus() },
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
