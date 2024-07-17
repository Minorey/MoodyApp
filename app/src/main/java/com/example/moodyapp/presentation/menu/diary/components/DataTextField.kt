package com.example.moodyapp.presentation.menu.diary.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterTextField(
    label: String,
    isWrote: Boolean,
    text: String,
    count: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 25.sp) },
        singleLine = true,
        enabled = isWrote,
        supportingText = { Text(text = count) },
        modifier = modifier,
        isError = text.isEmpty(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            errorIndicatorColor = MaterialTheme.colorScheme.secondary,
            disabledIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
            focusedTextColor = MaterialTheme.colorScheme.tertiary,
            disabledTextColor = MaterialTheme.colorScheme.tertiary,
            errorTextColor = MaterialTheme.colorScheme.tertiary,
            focusedSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            errorSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            disabledSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
            disabledLabelColor = MaterialTheme.colorScheme.tertiary,
            errorLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
    )

}

@Composable
fun CharacterContentTextField(
    label: String,
    count: String,
    isWrote: Boolean,
    text: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 25.sp) },
        singleLine = false,
        enabled = isWrote,
        supportingText = { Text(text = count) },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),

        isError = text.isEmpty(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            errorIndicatorColor = MaterialTheme.colorScheme.secondary,
            disabledIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
            focusedTextColor = MaterialTheme.colorScheme.tertiary,
            disabledTextColor = MaterialTheme.colorScheme.tertiary,
            errorTextColor = MaterialTheme.colorScheme.tertiary,
            focusedSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            errorSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            disabledSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
            disabledLabelColor = MaterialTheme.colorScheme.tertiary,
            errorLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
    )

}
