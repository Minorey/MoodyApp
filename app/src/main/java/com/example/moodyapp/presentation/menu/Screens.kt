package com.example.moodyapp.presentation.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.moodyapp.R
import com.example.moodyapp.presentation.nvgraph.Route

data class Screens(
    val screen: String = "",
    val name: String = "",
    val icon: ImageVector = Icons.Filled.Error,
) {
    //function to get the list of bottomNavigationItems
    //el orden de la lista es importante. de arriba hacia abajo == de izquierda a derecha
    @Composable
    fun bottomNavigationItems(): List<Screens> {
            return listOf(
            Screens(
                name = stringResource(id = R.string.memoriesName),
                icon = Icons.Filled.LibraryBooks,
                screen = Route.MyMemoriesScreen.route,
            ),
            Screens(
                name = stringResource(id = R.string.diaryName),
                icon = Icons.AutoMirrored.Filled.MenuBook,
                screen = Route.DiaryScreen.route
            ),
            Screens(
                name = stringResource(id = R.string.profileName),
                icon = Icons.Filled.Person,
                screen = Route.ProfileScreen.route
            ),
        )
    }
}

/*@Composable
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
}*/