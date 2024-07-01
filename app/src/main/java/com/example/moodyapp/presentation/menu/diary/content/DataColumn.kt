package com.example.moodyapp.presentation.menu.diary.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.moodyapp.presentation.menu.diary.components.CharacterTextField
import com.example.moodyapp.presentation.menu.diary.components.TitleText

@Composable
fun DataColumn(
    team: String,
    games: String,
    elements: String,
    onTeamChanged: (String) -> Unit,
    onGamesChanged: (String) -> Unit,
    onElementsChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TitleText(text = "1. Completa los Datos")
        CharacterTextField(label = "Nombre", text = team, onValueChange = onTeamChanged)
        CharacterTextField(label = "¿Como te sientes?", text = games, onValueChange = onGamesChanged)
        CharacterTextField(label = "Ultimo comentario", elements, onValueChange = onElementsChange)
        }
}
