package com.example.moodyapp.presentation.menu

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import com.example.moodyapp.presentation.menu.diary.Diary
import com.example.moodyapp.presentation.menu.mymemories.MyMemories
import com.example.moodyapp.presentation.menu.perfil.Profile
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(navController: NavController, dataStore: DataStore<Preferences>) {
//initializing the default selected item
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(initialPage = 1) {
        3
    }
    val context=LocalContext.current

    var color by remember {
        mutableStateOf(Color.Transparent)
    }
    if (isSystemInDarkTheme()) {
        color = MaterialTheme.colorScheme.background
    }else{
        color = MaterialTheme.colorScheme.tertiary
    }

    (context as? Activity)?.requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                //getting the list of bottom navigation items for our data class
                Screens().bottomNavigationItems().forEachIndexed { index, navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == pageState.currentPage,
                        label = {
                            Text(
                                text = navigationItem.name,
                                color = color
                            )
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.name,
                                tint = color,
                            )
                        },
                        onClick = {
                            scope.launch {
                                pageState.animateScrollToPage(page = index)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            //Color del item seleccionado
                            indicatorColor = MaterialTheme.colorScheme.secondary
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
                    0 -> MyMemories(navController = navController)
                    1 -> Diary(dataStore = dataStore)
                    2 -> Profile(navController)
                }
            }
        }
    }
}
