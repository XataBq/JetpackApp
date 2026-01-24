package com.example.testapp.ui.screens.auth.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onButtonClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(13.dp),
        onClick = onButtonClick,
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
