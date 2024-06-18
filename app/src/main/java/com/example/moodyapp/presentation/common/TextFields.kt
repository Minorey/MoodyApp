package com.example.moodyapp.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodyapp.R
import com.example.moodyapp.ui.theme.Quicksand


@Composable
fun NormalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    descriptionIcon: String,
    label: String,
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left,
            fontFamily = Quicksand,
            color = MaterialTheme.colorScheme.primary,
        ),
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = label,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = descriptionIcon,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
            .fillMaxWidth(OutlinedTextFieldDefaults.MinWidth.value / 10)
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    descriptionIcon: String,
    label: String,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left,
            fontFamily = Quicksand,
            color = MaterialTheme.colorScheme.primary,
        ),
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = label,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon, contentDescription = descriptionIcon
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
            .fillMaxWidth(OutlinedTextFieldDefaults.MinWidth.value / 10)
    )
}

@Composable
fun SoloLecturaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    descriptionIcon: String,
    click:()->Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = true,
        leadingIcon={
            Icon(
                imageVector = leadingIcon,
                contentDescription = descriptionIcon,
            )
        },
        label = {
                Text(text = stringResource(id = R.string.birthdateTitle))
        },
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left,
            fontFamily = Quicksand,
            color = MaterialTheme.colorScheme.primary,
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            click()
                            // works like onClick
                        }
                    }
                }
            },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
            .fillMaxWidth(OutlinedTextFieldDefaults.MinWidth.value / 10),
    )
}