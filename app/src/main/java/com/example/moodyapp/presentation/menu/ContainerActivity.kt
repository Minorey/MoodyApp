package com.example.moodyapp.presentation.menu

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodyapp.presentation.menu.calendar.Calendar
import com.example.moodyapp.presentation.menu.diary.Diary
import com.example.moodyapp.presentation.menu.perfil.Profile

@Composable
fun BottomNavigationBar() {
//initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
            ) {
                //getting the list of bottom navigation items for our data class
                Screens().bottomNavigationItems().forEachIndexed { index, navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(
                                text = navigationItem.name,
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.name,
                                tint = MaterialTheme.colorScheme.background,
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.screen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors =NavigationBarItemDefaults.colors(
                            //Color del item seleccionado
                            indicatorColor = if(isSystemInDarkTheme()){
                                //Si el tema es oscuro
                                MaterialTheme.colorScheme.secondary
                            } else {
                                //Si el tema es claro
                                MaterialTheme.colorScheme.surface
                            }
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.Diary.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Routes.Calendar.route) {
                Calendar()
            }
            composable(Routes.Diary.route) {
                Diary()
            }
            composable(Routes.Profile.route) {
                Profile()
            }
        }
    }
}