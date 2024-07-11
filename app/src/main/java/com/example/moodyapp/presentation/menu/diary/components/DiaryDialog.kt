package com.example.moodyapp.presentation.menu.diary.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodyapp.R
import com.example.moodyapp.presentation.menu.diary.content.RadioButtons


@Composable
fun EmotionalDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    Emotion: (String) -> Unit,
) {
    var emotionDia: String by remember {
        mutableStateOf("")
    }

    if (shown) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(onClick = {
                    confirmButton()
                }) {
                    Text(text = stringResource(R.string.ConfirmMsg))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.DismissMsg))
                }
            },
            text = {
                RadioButtons() {
                    emotionDia = it
                    Emotion(it)
                }

            },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.height(300.dp)
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun NoDiaryDialog(
    shown: Boolean,
    title: String,
    content: String,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
) {
    if (shown) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { confirmButton() }) {
                    Text(text = stringResource(R.string.ConfirmMsg))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.DismissMsg))
                }
            },
            title = {
                Text(text = title, modifier = Modifier.padding(start = 30.dp))
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = content,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    Image(
                        painter = painterResource(id = R.raw.mask),
                        contentDescription = "Happy tall llama",
                        modifier = Modifier
                            .padding(top = 20.dp, start = 15.dp)
                            .size(80.dp)
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.height(300.dp)
        )
    }
}
