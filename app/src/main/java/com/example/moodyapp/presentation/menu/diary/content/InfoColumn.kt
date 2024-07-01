package com.example.moodyapp.presentation.menu.diary.content

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.moodyapp.LogoGeneratorViewModel
import com.example.moodyapp.presentation.menu.diary.components.ActionButton
import com.example.moodyapp.presentation.menu.diary.components.TitleText

@Composable
fun InfoColumn(context: Context, viewModel: LogoGeneratorViewModel) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        TitleText("2. Añade información")

        ActionButton(
            if (viewModel.recording) "Detener grabación" else "Iniciar grabación",
            Icons.Filled.Mic,
            "Grabación de audio") {

            viewModel.recordAudio(context)
        }

        if (viewModel.info.isNotEmpty()) {

            ActionButton("Resumir", Icons.Filled.Compress,"Resume la grabación") {
                viewModel.createInfoSummary()
            }

            Text(viewModel.info)
        }
    }
}