package com.example.moodyapp.presentation.nvgraph

sealed class Route (
    val route: String,
    //val arguments: List<NamedNavArgument> = emptyList()
    ) {
        object OnBoardingScreen : Route(route = "onBoardingScreen")

        object HomeScreen : Route(route = "homeScreen")

        object SearchScreen : Route(route = "searchScreen")

        object LoginScreen : Route(route = "loginScreen")

        object DetailsScreen : Route(route = "detailsScreen")

        object AppStartNavigation : Route(route = "appStartNavigation")

        object MoodyNavigation : Route(route = "moodyNavigation")

        object MoodyNavigatorScreen: Route(route = "moodyNavigator" )
}