package com.example.testapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.models.MessageKind
import com.example.testapp.ui.models.ValidationState

@Composable
fun ValidationBlock(state: ValidationState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) { }
    when (state) {
        ValidationState.None -> Unit
        is ValidationState.Error ->
            ValidationMessage(
                message = state.message,
                kind = MessageKind.Error,
            )

        is ValidationState.Success ->
            ValidationMessage(
                message = state.message,
                kind = MessageKind.Success,
            )
    }
}

@Composable
fun ValidationMessage(
    message: String,
    kind: MessageKind,
) {
    val color =
        when (kind) {
            MessageKind.Success -> MaterialTheme.colorScheme.primary
            MessageKind.Error -> MaterialTheme.colorScheme.error
        }
    Text(
        text = message,
        style = MaterialTheme.typography.headlineMedium,
        color = color,
        modifier =
            Modifier
                .padding(horizontal = 40.dp, vertical = 20.dp),
    )
}
