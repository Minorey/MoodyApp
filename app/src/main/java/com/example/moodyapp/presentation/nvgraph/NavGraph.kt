package com.example.moodyapp.presentation.nvgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.moodyapp.presentation.login.LoginScreen
import com.example.moodyapp.presentation.menu.BottomNavigationBar
import com.example.moodyapp.presentation.menu.Routes
import com.example.moodyapp.presentation.menu.calendar.Calendar
import com.example.moodyapp.presentation.menu.diary.Diary
import com.example.moodyapp.presentation.menu.perfil.Profile
import com.example.moodyapp.presentation.onboarding.horizontalViewPager
import com.example.moodyapp.presentation.register.ui.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    startDestination: String,
    dataStore: DataStore<Preferences>,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination) {
        composable(route = startDestination) {
            horizontalViewPager(
                navController = navController,
                dataStore = dataStore
            )
        }
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Route.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(route = Route.MenuScreen.route) {
            BottomNavigationBar()
        }

        composable(route = Routes.Diary.route) {
            Diary()
        }
        composable(route = Routes.Calendar.route) {
            Calendar()
        }
        composable(route = Routes.Profile.route) {
            Profile()
        }

    }
}