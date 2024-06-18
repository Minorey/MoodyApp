package com.example.moodyapp.presentation.menu

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Screens(
    val screen: String = "",
    val name: String = "",
    val icon: ImageVector = Icons.Filled.Error,
) {
    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems(): List<Screens> {
        return listOf(
            Screens(
                name = "Diary",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                screen = Routes.Diary.route,
            ),
            Screens(
                name = "Calendar",
                icon = Icons.Filled.CalendarMonth,
                screen = Routes.Calendar.route
            ),
            Screens(
                name = "Profile",
                icon = Icons.Filled.Person,
                screen = Routes.Profile.route
            ),
        )
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = 0.5f)
) {
}