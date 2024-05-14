package com.example.moodyapp.presentation.nvgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.moodyapp.presentation.login.LoginScreen
import com.example.moodyapp.presentation.onboarding.OnBoardingScreen
import com.example.moodyapp.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.OnBoardingScreen.route
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
                LoginScreen()
            }
        }
    }
}