package com.example.moodyapp

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.moodyapp.presentation.nvgraph.NavGraph
import com.example.moodyapp.presentation.nvgraph.Route
import com.example.moodyapp.ui.theme.MoodyAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore by preferencesDataStore("USER_PREFERENCES")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MoodyAppTheme(
                dynamicColor = true
            ) {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent, darkIcons = !isSystemInDarkMode
                    )

                }
                NavGraph(startDestination = Route.AppStartNavigation.route, dataStore)
            }
        }
    }
}

