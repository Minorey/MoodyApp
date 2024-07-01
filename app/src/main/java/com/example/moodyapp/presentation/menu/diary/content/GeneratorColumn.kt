package com.example.moodyapp.presentation.menu.diary.content

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moodyapp.LogoGeneratorViewModel
import com.example.moodyapp.presentation.menu.diary.components.ActionButton
import com.example.moodyapp.presentation.menu.diary.components.TitleText

@Composable
fun GeneratorColumn(
    context: Context,
    viewModel: LogoGeneratorViewModel,
    team: String,
    games: String,
    elements: String) {

    var imageURL by remember { mutableStateOf("") }
    var masked by remember { mutableStateOf(false) }

    val clipboard = LocalClipboardManager.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TitleText(text = "3. Genera tu LLAMA MOODY")
        ActionButton(
            text = "Generar",
            icon = Icons.Filled.Draw ,
            description = "Genera el Logotipo",
            enabled = team.isNotEmpty() && games.isNotEmpty() && elements.isNotEmpty()
        ) {
            viewModel.generateLogo(context, games, elements, masked) {
                imageURL = it
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mostrar imagen")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(masked, onCheckedChange = {masked = it})
        }

        if (imageURL.isNotEmpty()){
            AsyncImage(
                model = imageURL,
                contentDescription = "$team Logo",
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    clipboard.setText(AnnotatedString(imageURL))
                    Toast.makeText(context, "URL copiado", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    Icons.Filled.ContentCopy,
                    contentDescription = "Copiar URL",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                team,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp
            )
        }
        if (viewModel.error) {
            Text("Error al generar la imagen", color = Color.Red)
        }
    }
}