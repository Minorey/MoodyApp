package com.example.moodyapp.presentation.nvgraph

sealed class Route(
    val route: String,
    //val arguments: List<NamedNavArgument> = emptyList()
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")

    object LoginScreen : Route(route = "loginScreen")
    object RegisterScreen : Route(route = "registerScreen")
    object MenuScreen : Route(route = "menuScreen")
    object MyMemoriesScreen : Route(route = "mymemoriesScreen")
    object DiaryScreen : Route(route = "diaryScreen")
    object ProfileScreen : Route(route = "profileScreen")
    object NewScreenWithConf : Route(route = "MyConfScreen")
    object ConfScreen : Route(route = "settingsScreen")
    object MyPageScreen : Route(route = "myPageScreen")


    object AppStartNavigation : Route(route = "appStartNavigation")

    object MoodyNavigation : Route(route = "moodyNavigation")

    object MoodyNavigatorScreen : Route(route = "moodyNavigator")

}