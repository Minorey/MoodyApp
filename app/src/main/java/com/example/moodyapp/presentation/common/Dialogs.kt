package com.example.moodyapp.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.moodyapp.R

@Composable
fun SimpleAlertDialog(
    shown:Boolean,
    onDismissRequest:()->Unit,
    confirmButton:() ->Unit,
    title:String,
    description:String,
    icondialog: ImageVector,
){
    if (shown) {
        AlertDialog(
            onDismissRequest = {onDismissRequest()},
            confirmButton = {
                TextButton(onClick = {confirmButton()}) {
                    Text(text = stringResource(R.string.ConfirmMsg))
                }
            },
            title = {
                Text(text=title)
            },
            text = {
                Text(text=description)
            },
            icon = {
                Icon(imageVector = icondialog, contentDescription = "IconDialog")
            }

        )
    }
}