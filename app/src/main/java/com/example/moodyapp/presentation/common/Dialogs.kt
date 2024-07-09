package com.example.moodyapp.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moodyapp.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import kotlin.time.Duration.Companion.days

@Composable
fun SimpleAlertDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    title: String,
    description: String,
    icondialog: ImageVector,
) {
    if (shown) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { confirmButton() }) {
                    Text(text = stringResource(R.string.ConfirmMsg))
                }
            },
            dismissButton = {TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.DismissMsg))
            }},
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description)
            },
            icon = {
                Icon(imageVector = icondialog, contentDescription = "IconDialog")
            },
            containerColor = MaterialTheme.colorScheme.background,

            )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        yearRange = (LocalDate.now().year - 100)..(LocalDate.now().year - 10),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = "Cancel")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = stringResource(id = R.string.birthdateTitle),
                    modifier = Modifier.padding(20.dp, 20.dp)
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            )

        )
    }
}

private fun convertMillisToDate(millis: Long): String {

    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis.plus(1.days.inWholeMilliseconds)))
}