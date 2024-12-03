package com.example.moodyapp.presentation.menu.diary.content

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.moodyapp.presentation.menu.diary.TextGeneratorViewModel
import com.example.moodyapp.presentation.menu.diary.components.CharacterTextField
import com.example.moodyapp.util.Env

@Composable
fun APIKeyRow(
    context: Context,
    viewModel: TextGeneratorViewModel,
    apiKey: String,
    onApiKeyChanged: (String) -> Unit
) {
    if (Env.CUSTOM_OPENAI_API_KEY.isEmpty()) {
        var currentApiKey = apiKey
        if (apiKey.isEmpty()) {
            currentApiKey = viewModel.getCustomAPIKey(context)
            onApiKeyChanged(currentApiKey)
        }

        Row {
            CharacterTextField(
                label = "Open AI API Key",
                count = "",
                isWrote = true,
                text = currentApiKey,
                onValueChange = {

                    viewModel.setCustomAPIKey(context, if (it.length == 51) it else "")
                    onApiKeyChanged(it)
                })
        }
    }
}
