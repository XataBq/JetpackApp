package com.example.testapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import com.example.testapp.ui.theme.TestAppTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.R
import com.example.testapp.ui.theme.subtitle

@Composable
fun HomeScreen() {
    TestAppTheme {
        Scaffold(
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(Color.LightGray)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GreetingKamilla()
                    Spacer(modifier = Modifier.height(30.dp))
                    MainScreenButtons()
                    Spacer(modifier = Modifier.height(30.dp))
                    StartImageButton()
                }
            }
        )
    }
}

@Composable
fun GreetingKamilla() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi, Kamilla",
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = "Hi, Kamilla. Love you very much!",
            color = MaterialTheme.colorScheme.subtitle,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
@Composable
fun StudyAppHeader(
    title: String = "",
    subtitle: String = "",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = subtitle,
            color = MaterialTheme.colorScheme.subtitle,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}



@Composable
fun MainScreenButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1F)
        ) {
            Text("Kami")
        }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1F)
        ) {
            Text("My Kami")
        }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1F)
        ) {
            Text("My Kami")
        }
    }
}

@Composable
fun StartImageButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_correct),
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .shadow(5.dp, shape = RoundedCornerShape(24.dp))
                .clip(shape = RoundedCornerShape(24.dp))
                .clickable(
                    onClick = {},
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() },
                )
        )
        Text(
            text = "Correct answer!",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
        )
    }

}

@Composable
@Preview
fun MainCheckbox() {
    var isChecked: Boolean by remember { mutableStateOf(true) }
    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            Log.i("!!!", "MainCheckbox: $it")
            isChecked = it
        },
        modifier = Modifier.graphicsLayer(scaleX = 4f, scaleY = 4f),
    )
}

@Composable
@Preview(showBackground = true)
fun GreetingKamillaPreview() {
    GreetingKamilla()
}

@Composable
@Preview(showBackground = true)
fun MainScreenButtonsPreviewLight() {
    TestAppTheme (
        darkTheme = false
    ){
        MainScreenButtons()
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenButtonsPreviewDark() {
    TestAppTheme(
        darkTheme = true
    ) {
        MainScreenButtons()
    }
}

@Composable
@Preview(showBackground = true)
fun StartImageButtonPreview() {
    StartImageButton()
}