package com.example.moodyapp.presentation.menu.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodyapp.LogoGeneratorViewModel
import com.example.moodyapp.R
import com.example.moodyapp.presentation.menu.diary.content.APIKeyRow
import com.example.moodyapp.presentation.menu.diary.content.DataColumn
import com.example.moodyapp.presentation.menu.diary.content.GeneratorColumn
import com.example.moodyapp.presentation.menu.diary.content.InfoColumn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Diary() {
    val context = LocalContext.current
    val viewModel = LogoGeneratorViewModel()
    val scrollState = rememberScrollState()

    var apiKey by remember { mutableStateOf("") }

    var team by remember { mutableStateOf("") }
    var games by remember { mutableStateOf("") }
    var elements by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth().padding(0.dp,20.dp,0.dp,0.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Icono",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MOODY - WHISPER IMAGE + TEXT)",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->

        if (viewModel.loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .alpha(if (viewModel.loading) 0.5f else 1f)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            // API Key
            APIKeyRow(context, viewModel, apiKey) {
                apiKey = it
            }

            if (!viewModel.apiError) {

                // 1
                DataColumn(
                    team,
                    games,
                    elements,
                    onTeamChanged = { team = it },
                    onGamesChanged = { games = it },
                    onElementsChange = { elements = it }
                )

                // 2
                InfoColumn(context, viewModel)


                // 3
                GeneratorColumn(context, viewModel, team, games, elements)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DiaryPreview() {
    Diary()
}
