package com.example.moodyapp.presentation.menu.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.ContactEmergency
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.moodyapp.R
import com.example.moodyapp.presentation.common.ProfileButton
import com.example.moodyapp.presentation.nvgraph.Route

@Composable
fun Profile(navigationController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp,20.dp, 0.dp,20.dp)//Margin
                .border(0.dp,Color.Transparent)
        ) {
            Column {
                Box(modifier = Modifier
                    .clickable {
                        /*TO-DO*/
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.onboardinghome1),
                        contentDescription = "MiprofileImage",
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(CircleShape)
                            .zIndex(1f),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .height(80.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row {
                    Text(
                        text = "My Example Name",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )//Nombre de Usuario
                }
                Row {
                    Text(text = "myexamplemail@gmail.com")//Correo
                }

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp,20.dp, 0.dp,0.dp)
            ) {//Botón de Configuraciones
                IconButton(onClick = { navigationController.navigate(Route.NewScreenWithConf.route) }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings, contentDescription = "settings icon"
                    )
                }
            }
        }
        ProfileButton(
            stringResource(id = R.string.myinfobtn),
            {/*TO-DO*/ },
            Icons.Outlined.ContactEmergency
        )
        ProfileButton(
            stringResource(id = R.string.mystatsbtn),
            {/*TO-DO*/ },
            Icons.Outlined.Analytics
        )
        ProfileButton(
            stringResource(id = R.string.techsupportbtn),
            {/*TO-DO*/ },
            Icons.Outlined.SupportAgent
        )
        ProfileButton(
            stringResource(id = R.string.conditionsbtn), {/*TO-DO*/ },
            Icons.AutoMirrored.Outlined.LibraryBooks
        )
        ProfileButton(
            stringResource(id = R.string.signoffbtn),
            {
                navigationController.navigate(Route.AppStartNavigation.route)
            },
            Icons.AutoMirrored.Outlined.ExitToApp
        )
    }
}