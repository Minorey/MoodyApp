package com.example.moodyapp.presentation.nvgraph

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.moodyapp.presentation.menu.Screens
import com.example.moodyapp.presentation.menu.calendar.Calendar
import com.example.moodyapp.presentation.menu.diary.Diary
import com.example.moodyapp.presentation.menu.perfil.Profile
import com.example.moodyapp.presentation.example.MyExampleScreen
import com.example.moodyapp.presentation.login.LoginScreen
import com.example.moodyapp.presentation.menu.BottomNavigationBar
import com.example.moodyapp.presentation.menu.Routes
import com.example.moodyapp.presentation.onboarding.OnBoardingScreen
import com.example.moodyapp.presentation.onboarding.OnBoardingViewModel
import com.example.moodyapp.presentation.register.ui.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()
    val activity = (LocalContext.current as? Activity)

    NavHost(navController = navController, startDestination){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.AppStartNavigation.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onEvent = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.MoodyNavigation.route,
            startDestination = Route.MoodyNavigatorScreen.route,
        ){
            composable(route = Route.MoodyNavigatorScreen.route){
                LoginScreen(navController)
            }
        }

        navigation(
            route = Route.MenuScreen.route,
            startDestination = Route.LoginScreen.route,
        ){
            composable(route = Route.LoginScreen.route){
                BackHandler(true) {
                    // Or do nothings
                    activity?.finish()
                }
                LoginScreen(navController = navController)
            }
        }
        navigation(
            route = Route.RegisterScreen.route,
            startDestination = Route.LoginScreen.route,
        ){
            composable(route = Route.LoginScreen.route){
                RegisterScreen(navController)
            }
        }
        navigation(route=Route.MenuScreen.route,
        startDestination=Route.LoginScreen.route){
            composable(route=Route.MenuScreen.route){
                BottomNavigationBar()
            }

            composable(route= Routes.Diary.route){
                Diary()
            }
            composable(route=Routes.Calendar.route){
                Calendar()
            }
            composable(route=Routes.Profile.route){
                Profile()
            }
        }
    }
}