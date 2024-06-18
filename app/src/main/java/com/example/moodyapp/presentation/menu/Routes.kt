package com.example.moodyapp.presentation.menu

sealed class Routes(val route : String) {
    object Diary : Routes("diary")
    object Calendar : Routes("calendar")
    object Profile : Routes("profile")
}