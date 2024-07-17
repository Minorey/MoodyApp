package com.example.moodyapp.presentation.register.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodyapp.R
import com.example.moodyapp.presentation.common.LinkButton
import com.example.moodyapp.presentation.common.MoodyButton
import com.example.moodyapp.presentation.common.MyDatePickerDialog
import com.example.moodyapp.presentation.common.NormalTextField
import com.example.moodyapp.presentation.common.PasswordTextField
import com.example.moodyapp.presentation.common.SimpleAlertDialog
import com.example.moodyapp.presentation.common.SoloLecturaTextField
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.database.database

@Composable
fun RegisterScreen(navController: NavHostController) {

    var username by remember {
        mutableStateOf("")
    }
    var userReg by remember {
        mutableStateOf("")
    }
    var passReg by remember {
        mutableStateOf("")
    }
    var shownReg by rememberSaveable {
        mutableStateOf(false)
    }
    var shownPass by rememberSaveable {
        mutableStateOf(false)
    }
    var shownUser by rememberSaveable {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf("dd-mm-yyyy")
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val maxCharuser by rememberSaveable { mutableIntStateOf(20) }
    var numCharuser by rememberSaveable { mutableIntStateOf(0) }
    val database = Firebase.database.reference

    val context = LocalContext.current

    (context as? Activity)?.requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 40.dp,
            alignment = Alignment.CenterVertically,
        )
    ) {
        Row {
            Column {
                Text(
                    text = stringResource(R.string.registerTitle),
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(R.string.subtitleRegister),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
        Row {
            Column {

                NormalTextField(
                    value = username,
                    onValueChange = { if (it.length <= maxCharuser) {
                        username = it
                        numCharuser = it.length
                    } },
                    leadingIcon = Icons.Filled.Person,
                    count = "$numCharuser/$maxCharuser",
                    descriptionIcon = "Username",
                    label = stringResource(R.string.username)
                )

                NormalTextField(
                    value = userReg,
                    onValueChange = { userReg = it },
                    leadingIcon = Icons.Filled.Abc,
                    descriptionIcon = "Email for Register",
                    label = stringResource(R.string.user)
                )

                PasswordTextField(
                    value = passReg,
                    onValueChange = { passReg = it },
                    leadingIcon = Icons.Filled.Lock,
                    descriptionIcon = "Password",
                    label = stringResource(R.string.pass),
                )

            }
        }
        Row {
            Column {
                if (showDatePicker) {
                    MyDatePickerDialog(onDateSelected = { date = it },
                        onDismiss = { showDatePicker = false })
                }
                Box(contentAlignment = Alignment.Center) {
                    SoloLecturaTextField(value = date,
                        onValueChange = { },
                        leadingIcon = Icons.Outlined.CalendarMonth,
                        descriptionIcon = "CalendarIcon",
                        click = { showDatePicker = true })
                }
            }
        }
        Row {
            Column {
                val context = LocalContext.current

                MoodyButton(text = stringResource(id = R.string.continueButton)) {
                    if (userReg.isNotEmpty() && passReg.isNotEmpty() && username.isNotEmpty() && date.isNotEmpty() && !date.contains(
                            "dd-mm-yyyy"
                        )
                    ) {
                        if(username.length >= 3){
                            if (passReg.length >= 8) {
                                if (passReg.matches(Regex(".*[0-9].*"))) {
                                    //Llamar LOGS de error
                                    if (passReg.matches(Regex(".*[A-Z@!*_.? ].*"))) {

                                        FirebaseApp.initializeApp(context)
                                        Firebase.auth
                                            .createUserWithEmailAndPassword(userReg, passReg)
                                            .addOnSuccessListener {

                                                //Realtime database data write
                                                val primalUser = database.child("users")
                                                    .child(Firebase.auth.currentUser?.uid.toString())
                                                primalUser.child("username").setValue(username)
                                                primalUser.child("email").setValue(userReg)
                                                primalUser.child("birthdate").setValue(date)

                                                //Confirm actions
                                                Thread.sleep(1_000)
                                                navController.navigate("menuScreen")

                                            }

                                    } else {
                                        shownPass = true
                                    }
                                } else {
                                    shownPass = true
                                }
                            } else {
                                shownPass = true
                            }
                        }else{
                            shownUser=true
                        }
                    } else {
                        shownReg = true
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(R.string.yesAccount),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(0.dp, 15.dp),
            )
            LinkButton(
                text = stringResource(R.string.SignInButtonLabel),
                onClick = { navController.navigate("loginScreen") })
        }
        MyNoContentDialogReg(shown = shownReg, { shownReg = false },
            { shownReg = false })

        MyNotPasswordCorrectDialog(shown = shownPass,
            onDismissRequest = { shownPass = false },
            { shownPass = false })

        MyNotUserCorrectDialog(shown = shownUser,
            onDismissRequest = { shownUser = false },
            { shownUser = false })
    }
}


@Composable
fun MyNoContentDialogReg(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    SimpleAlertDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
        title = stringResource(R.string.TitleAlertComplete),
        description = stringResource(R.string.ContentAlertComplete),
        icondialog = Icons.Filled.Error
    )
}

@Composable
fun MyNotUserCorrectDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    SimpleAlertDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
        title = stringResource(R.string.TitleAlertComplete),
        description = stringResource(R.string.errorUsernameCorrect),
        icondialog = Icons.Filled.Error
    )
}

@Composable
fun MyNotPasswordCorrectDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    SimpleAlertDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
        title = stringResource(R.string.TitleAlertComplete),
        description = stringResource(R.string.errorPasswordCorrect),
        icondialog = Icons.Filled.Error
    )
}