package com.example.moodyapp.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import com.example.moodyapp.presentation.nvgraph.Route
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import com.example.moodyapp.data.manger.LocalUserMangerImpl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviousNextButton(
    pagerState: PagerState,
    pageCount: Int,
    navController: NavHostController,
    dataStore: DataStore<Preferences>
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp, 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Button(onClick = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        }

        Box(
            modifier = Modifier
                .width(50.dp)
                .padding(0.dp, 20.dp)
        ) {
            Row {
                repeat(pageCount) { iteration ->
                    val color: Color = if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }
        }

        val preferences = LocalUserMangerImpl(dataStore)

        Button(
            onClick = {
                scope.launch {
                    if (pagerState.currentPage < pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        navController.navigate(Route.LoginScreen.route)
                        preferences.updatePreference(
                            LocalUserMangerImpl.ONBOARDING_CHECKED_KEY,
                            true
                        )
                    }
                }
            },
            shape = CircleShape,

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }


}
