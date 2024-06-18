package com.example.moodyapp.presentation.nvgraph

sealed class Route (
    val route: String,
    //val arguments: List<NamedNavArgument> = emptyList()
    ) {
        object OnBoardingScreen : Route(route = "onBoardingScreen")

        object LoginScreen : Route(route = "loginScreen")
        object RegisterScreen : Route(route = "registerScreen")
        object MenuScreen : Route(route = "menuScreen")


        object AppStartNavigation : Route(route = "appStartNavigation")

        object MoodyNavigation : Route(route = "moodyNavigation")

        object MoodyNavigatorScreen: Route(route = "moodyNavigator" )
}