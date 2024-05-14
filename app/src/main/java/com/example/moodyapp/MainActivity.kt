package com.example.moodyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodyapp.presentation.login.LoginScreen
import com.example.moodyapp.presentation.nvgraph.NavGraph
import com.example.moodyapp.presentation.onboarding.OnBoardingScreen
import com.example.moodyapp.presentation.onboarding.OnBoardingViewModel
import com.example.moodyapp.ui.theme.MoodyAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }

        setContent {
            MoodyAppTheme(

                dynamicColor = false
            ) {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController= rememberSystemUiController()

                SideEffect {

                    systemController.setSystemBarsColor(
                        color=Color.Transparent,
                        darkIcons=!isSystemInDarkMode
                    )

                }

                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    NavGraph(startDestination = viewModel.startDestination)
            }
        }


    }
}
}
