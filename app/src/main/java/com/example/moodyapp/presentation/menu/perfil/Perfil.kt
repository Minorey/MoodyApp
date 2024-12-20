package com.example.moodyapp.presentation.menu.perfil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.ContactEmergency
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun Profile(navigationController: NavController) {

    var username by remember {
        mutableStateOf("")
    }
    var correo by remember {
        mutableStateOf("")
    }

    //DAtabase
    val myRef = Firebase.database.reference.child("users").child(Firebase.auth.currentUser?.uid.toString())
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                username = dataSnapshot.child("username").value.toString()
                correo = dataSnapshot.child("email").value.toString()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.e("ERROR DATABASE", error.toString())
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 40.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 20.dp)//Margin
                .border(0.dp, Color.Transparent)
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
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .clip(CircleShape)
                            .zIndex(1f),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp)
                    .width(160.dp)
                    .height(80.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                    Text(
                        text = username,
                        style = if(username.length<=10){
                            MaterialTheme.typography.displayMedium
                        } else{
                            MaterialTheme.typography.displaySmall
                        },
                        fontWeight = FontWeight.Bold,
                    )//Nombre de Usuario
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
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
                Firebase.auth.signOut()
                navigationController.navigate(Route.AppStartNavigation.route)
            },
            Icons.AutoMirrored.Outlined.ExitToApp
        )
    }
}