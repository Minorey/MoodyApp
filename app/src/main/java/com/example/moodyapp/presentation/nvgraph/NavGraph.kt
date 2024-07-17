package com.example.moodyapp.presentation.nvgraph

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.moodyapp.page.MyPage
import com.example.moodyapp.presentation.confscreen.MySettings
import com.example.moodyapp.presentation.login.LoginScreen
import com.example.moodyapp.presentation.menu.BottomNavigationBar
import com.example.moodyapp.presentation.onboarding.horizontalViewPager
import com.example.moodyapp.presentation.register.ui.RegisterScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraph(
    startDestination: String,
    dataStore: DataStore<Preferences>,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination) {

        //el onboarding, No tocar pls
        composable(route = startDestination) {
            horizontalViewPager(
                navController = navController,
                dataStore = dataStore
            )
        }

        //Login
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController)
        }
        //Register
        composable(route = Route.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(route = Route.MenuScreen.route) {
            BackHandler(enabled = true) {
                // do nothing
            }
            BottomNavigationBar(navController,dataStore)
        }

        //Navegación a las Configuraciones
        navigation(
            route = Route.NewScreenWithConf.route,
            startDestination = Route.ConfScreen.route
        ) {
            composable(Route.ConfScreen.route) {
                MySettings()
            }
        }
        //Navegación a MyPage
        composable(
            route = Route.MyPageScreen.route + "/{mydate}",
        ) { backStackEntry ->
            val myactualdate: String = backStackEntry.arguments?.getString("mydate") ?: ""
            MyPage(mydate = myactualdate)
        }

    }
}