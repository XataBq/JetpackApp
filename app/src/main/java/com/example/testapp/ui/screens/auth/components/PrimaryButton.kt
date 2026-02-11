package com.example.testapp.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    text: String,
    onButtonClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = {
            onButtonClick()
        },
        modifier =
            Modifier
                .height(56.dp)
                .padding(40.dp, 0.dp)
                .fillMaxWidth(),
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}
