package com.example.moodyapp.presentation.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodyapp.ui.theme.MoodyAppTheme

class Example : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                }
            }
        }
    }
}
@Composable
fun MyExampleScreen() {
    Text(
        text = "Example Text Test"
    )
    Text(
        text = "Example Text Test"
    )
    Text(
        text = "Example Text Test"
    )
    Text(
        text = "Example Text Test"
    )
    Text(
        text = "Example Text Test"
    )
}
