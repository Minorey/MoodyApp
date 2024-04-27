package com.example.moodyapp.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodyapp.MainActivity
import com.example.moodyapp.R
import com.example.moodyapp.presentation.common.GoogleButton
import com.example.moodyapp.presentation.common.LinkButton
import com.example.moodyapp.presentation.common.LoginButton
import com.example.moodyapp.presentation.common.NormalTextField
import com.example.moodyapp.presentation.common.PasswordTextField
import com.example.moodyapp.ui.theme.MoodyAppTheme

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodyAppTheme {

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
                    text = stringResource(R.string.greetings),
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(R.string.subtitleLogin),
                    style =MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
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

                NormalTextField(
                    value = user,
                    onValueChange = {user=it},
                    leadingIcon = Icons.Filled.Person,
                    descriptionIcon = "User",
                    label = stringResource(R.string.user)
                )

                PasswordTextField(
                    value = pass,
                    onValueChange = {pass=it},
                    leadingIcon = Icons.Filled.Lock,
                    descriptionIcon = "Password",
                    label = stringResource(R.string.pass)
                )

                LinkButton(text = stringResource(R.string.forgotPass), onClick = { /*TODO*/ })
            }
        }
        Row {
            Column {
                val context = LocalContext.current
                LoginButton(
                    text = stringResource(R.string.SignInButtonLabel),
                    onClick = { context.startActivity(Intent(context, MainActivity::class.java)) })

                //GoogleButtom
                GoogleButton(text = stringResource(id = R.string.googleSignin))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = stringResource(R.string.noAccount),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(0.dp, 15.dp),
                    )
                    LinkButton(text = stringResource(R.string.registerHere), onClick = { /*TODO*/ })
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