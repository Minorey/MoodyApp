package com.example.moodyapp.presentation.menu.diary.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterTextField(label: String, text: String,count: String, onValueChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 25.sp) },
        singleLine = true,
        supportingText = { Text(text = count) },
        modifier = Modifier.fillMaxWidth(),
        isError = text.isEmpty(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            errorIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            disabledIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            errorTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedSupportingTextColor = MaterialTheme.colorScheme.onPrimary,
            errorSupportingTextColor = MaterialTheme.colorScheme.onPrimary,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            disabledLabelColor = MaterialTheme.colorScheme.onPrimary,
            errorLabelColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )

}

@Composable
fun CharacterContentTextField(
    label: String,
    count: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 25.sp) },
        singleLine = false,
        supportingText = { Text(text = count) },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            ,

        isError = text.isEmpty(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            errorIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.primary,
            errorTextColor = MaterialTheme.colorScheme.primary,
            focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
            errorSupportingTextColor = MaterialTheme.colorScheme.primary,
            disabledSupportingTextColor = MaterialTheme.colorScheme.primary,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.primary,
            errorLabelColor = MaterialTheme.colorScheme.primary,
        ),
    )

}
