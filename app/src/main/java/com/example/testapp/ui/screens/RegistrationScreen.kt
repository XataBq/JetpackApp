package com.example.testapp.ui.screens

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.testapp.R
import com.example.testapp.ui.components.ValidationBlock
import com.example.testapp.ui.models.ValidationState
import com.example.testapp.ui.theme.labelTextField

@Composable
fun RegistrationScreen() {

    var userEmail: String by remember { mutableStateOf("") }
    var isEmailFormatValid: Boolean by remember { mutableStateOf(true) }
    var validationState: ValidationState by remember {
        mutableStateOf<ValidationState>(
            ValidationState.None
        )
    }

    val haptic = LocalHapticFeedback.current
    val testEmail: String = "example@king.ru"

    Spacer(modifier = Modifier.height(70.dp))
    StudyAppHeader(
        title = "Регистрация",
        subtitle = "Введите почту"
    )
    Spacer(modifier = Modifier.height(200.dp))
    CheckEmailField(
        email = userEmail,
        isEmailValid = isEmailFormatValid,
        onEmailChange = {
            userEmail = it
            isEmailFormatValid =
                if (it.isNotEmpty()) EMAIL_ADDRESS.matcher(it).matches()
                else true
            validationState = ValidationState.None
        },
        onClearClicked = {
            userEmail = ""
            isEmailFormatValid = true
            validationState = ValidationState.None
        }
    )
    Spacer(modifier = Modifier.height(20.dp))
    PrimaryButton(
        text = "Register",
        onRegistryClick = {
            validationState = if (userEmail.isEmpty() || !isEmailFormatValid) {
                haptic.performHapticFeedback(
                    hapticFeedbackType = HapticFeedbackType.TextHandleMove
                )
                ValidationState.Error(
                    "Некорректный Email адрес. Проверьте правильность введенных данных"
                )
            } else if (userEmail == testEmail) {
                haptic.performHapticFeedback(
                    hapticFeedbackType = HapticFeedbackType.TextHandleMove
                )
                ValidationState.Error(
                    "Аккаунт с такой почтой уже зарегистрирован.\n" +
                            "Проверьте правильность введенных данных"
                )

            } else {
                haptic.performHapticFeedback(
                    hapticFeedbackType = HapticFeedbackType.TextHandleMove
                )
                ValidationState.Success("Аккаунт успешно зарегистрирован!")
            }
        },
    )
    ValidationBlock(validationState)
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
//            textState = it
//            errorState =
//                if (EMAIL_ADDRESS.matcher(it).matches()) ""
//                else "Wrong Email"
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
//                textState = ""
//                errorState = ""
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
    val haptic = LocalHapticFeedback.current
    val state: ValidationState
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