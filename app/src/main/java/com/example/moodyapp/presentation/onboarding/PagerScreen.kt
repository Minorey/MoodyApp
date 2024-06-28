package com.example.moodyapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import com.example.moodyapp.data.manger.LocalUserMangerImpl
import com.example.moodyapp.dataStore
import com.example.moodyapp.presentation.nvgraph.Route
import com.example.moodyapp.presentation.onboarding.components.PreviousNextButton
import com.example.moodyapp.presentation.onboarding.components.SinglePage
import com.example.moodyapp.presentation.onboarding.components.pagesList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun horizontalViewPager(navController: NavHostController, dataStore: DataStore<Preferences>) {
    val preferences = LocalUserMangerImpl(dataStore)
    val isOnBoardingChecked =
        preferences.getPreferenceFlow(LocalUserMangerImpl.ONBOARDING_CHECKED_KEY, false)
            .collectAsState(initial = false)
    if (isOnBoardingChecked.value) {
        navController.navigate(Route.LoginScreen.route)
    } else {
        val pages = pagesList.size;//Número de páginas
        val statePager = rememberPagerState {
            0//página inicial
            0f//tamaño diferencial de la página principal
            pages//número de páginas
        }
        Row {
            HorizontalPager(state = statePager, pageSize = PageSize.Fill) { index ->
                SinglePage(page = pagesList[index])
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row{
            PreviousNextButton(pagerState = statePager, pages, navController, dataStore)
        }
    }
}
