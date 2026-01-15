package com.example.testapp.ui.screens.register

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.R
import com.example.testapp.ui.components.ValidationBlock
import com.example.testapp.ui.models.ValidationState
import com.example.testapp.ui.screens.StudyAppHeader
import com.example.testapp.ui.theme.labelTextField

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel()
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
        onClearClicked = viewModel::onClearClicked
    )
    Spacer(modifier = Modifier.height(20.dp))
    PrimaryButton(
        text = "Register",
        onRegistryClick = viewModel::onRegistryClick,
    )
    ValidationBlock(uiState.validationState)
}

@Composable
fun CheckEmailField(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,
) {
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = {
            Text(
                text = if (isEmailValid) "Email" else "Email Error",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.labelTextField
            )
        },
        placeholder = {
            Text(
                text = "123456789@mail.ru",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.labelTextField
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onClearClicked()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "clear email field"
                )
            }
        },
        shape = RoundedCornerShape(13.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 10.dp),
        singleLine = true,
        isError = !isEmailValid
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onRegistryClick: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = {
            onRegistryClick()
        },
        modifier = Modifier
            .height(56.dp)
            .padding(40.dp, 0.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Register",
        onRegistryClick = {},
    )
}