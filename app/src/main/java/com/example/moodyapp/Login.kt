package com.example.moodyapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodyapp.ui.theme.MoodyAppTheme
import com.example.moodyapp.ui.theme.quicksandFontFamily

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 40.dp,
            alignment = Alignment.CenterVertically,
        )
    ) {
        Row {
            Column {
                Text(
                    fontFamily = quicksandFontFamily,
                    text = stringResource(R.string.greetings),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    fontFamily = quicksandFontFamily,
                    text = stringResource(R.string.subtitleLogin),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Row {
            Column {
                var user by remember {
                    mutableStateOf("")
                }
                var pass by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = user,
                    onValueChange = {
                        user = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.tertiary,
                    ),
                    label = {
                        Text(fontFamily = quicksandFontFamily, text = stringResource(R.string.user))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person, contentDescription = "User"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,


                        ),
                    shape = RoundedCornerShape(25.dp),
                )
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(
                    value = pass,
                    onValueChange = {
                        pass = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Left,
                    ),
                    label = {
                        Text(fontFamily = quicksandFontFamily, text = stringResource(R.string.pass))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock, contentDescription = "Password"
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
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
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    shape = RoundedCornerShape(25.dp),
                )
                TextButton(
                    onClick = {
                        /*TODO*/
                    }, Modifier.padding(120.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        fontFamily = quicksandFontFamily,
                        text = stringResource(R.string.forgotPass),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
        }
        Row {
            Column {
                val context = LocalContext.current
                Button(
                    onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    },
                    modifier = Modifier
                        .width(OutlinedTextFieldDefaults.MinWidth)
                        .height(46.dp)
                        .padding(0.dp)
                ) {
                    Text(
                        fontFamily = quicksandFontFamily,
                        text = stringResource(R.string.SignInButtonLabel),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.background,
                    )
                }

                //GoogleButtom
                Button(
                    onClick = {
                        //startForResult.launch(googleSignInClient?.signInIntent)
                    },
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .width(OutlinedTextFieldDefaults.MinWidth)
                        .height(46.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_google),
                        contentDescription = "GoogleLogo",
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            .size(20.dp),
                    )
                    Text(
                        fontFamily = quicksandFontFamily,
                        text = "Sign in with Google",
                        modifier = Modifier.padding(0.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        fontFamily = quicksandFontFamily, text = stringResource(R.string.noAccount),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(0.dp, 15.dp),
                    )
                    TextButton(
                        onClick = {
                            /*TODO*/
                        }, Modifier.padding(0.dp)
                    ) {
                        Text(
                            fontFamily = quicksandFontFamily,
                            text = stringResource(R.string.registerHere),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MoodyAppTheme {
        LoginScreen()
    }
}