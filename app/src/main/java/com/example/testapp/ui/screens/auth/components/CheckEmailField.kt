package com.example.testapp.ui.screens.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.ui.theme.labelTextField

@Composable
fun CheckEmailField(
    email: String,
    formatError: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,
    onDone: () -> Unit,
) {
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = {
            Text(
                text = if (!formatError) "Email" else "Email Error",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.labelTextField,
            )
        },
        placeholder = {
            Text(
                text = "123456789@mail.ru",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.labelTextField,
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onClearClicked()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "clear email field",
                )
            }
        },
        shape = RoundedCornerShape(13.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 10.dp),
        singleLine = true,
        isError = formatError,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
    )
}
