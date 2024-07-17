package com.example.moodyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.preferencesDataStore
import com.example.moodyapp.presentation.nvgraph.NavGraph
import com.example.moodyapp.presentation.nvgraph.Route
import com.example.moodyapp.ui.theme.MoodyAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore by preferencesDataStore("USER_PREFERENCES")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val screenSplash= installSplashScreen()

        screenSplash.setKeepOnScreenCondition{false}

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

