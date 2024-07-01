package com.example.moodyapp.presentation.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.moodyapp.presentation.confscreen.MySettings
import com.example.moodyapp.presentation.menu.calendar.Calendar
import com.example.moodyapp.presentation.menu.diary.Diary
import com.example.moodyapp.presentation.menu.perfil.Profile
import com.example.moodyapp.presentation.nvgraph.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
//initializing the default selected item
    val scope = rememberCoroutineScope()
    var pageState = rememberPagerState(initialPage = 1) {
        3
    }

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
                        selected = index == pageState.currentPage,
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
                            scope.launch {
                                pageState.animateScrollToPage(page = index)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            //Color del item seleccionado
                            indicatorColor = if (isSystemInDarkTheme()) {
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
    ) { innerPaddingValues ->
        Box(
            modifier = Modifier.padding(innerPaddingValues)
        ) {
            HorizontalPager(
                state = pageState
            ) { page ->
                when (page) {
                    0 -> Calendar()
                    1 -> Diary()
                    2 -> Profile(navController)
                }
            }
        }
    }
}
