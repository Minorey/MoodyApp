package com.example.moodyapp.presentation.menu.diary

import android.content.ContentValues.TAG
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.moodyapp.util.Conf
import com.example.moodyapp.util.Env
import kotlinx.coroutines.launch
import javax.inject.Inject


class TextGenViewModel @Inject constructor() : ViewModel() {
    val gptQuery = mutableStateOf("")

    val word = mutableStateOf("")

    @OptIn(BetaOpenAI::class)
    fun getGPTResponse(textToSpeech: TextToSpeech) {
        viewModelScope.launch {
            val openAI = OpenAI(Env.OPENAI_API_KEY)

            try {
                val chatCompletionRequest = ChatCompletionRequest(
                    model = ModelId(Conf.GPT_MODEL),
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.System,
                            content ="eres un asistente psicológico que responde a las peticiones del usuario"
                        ),
                        ChatMessage(
                            role = ChatRole.User,
                            content ="Me das un consejo acerca de un diario que te he escrito lo siguiente, ${gptQuery.value}"
                        )
                    )
                )

                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

                val response = completion.choices.first().message?.content

                say(textToSpeech, response)
            } catch (e: Exception) {
                Log.d(TAG, "getGPTResponse: ERROR: ${e.message ?: ""}")
            }
        }
    }

    private fun say(textToSpeech: TextToSpeech, response: String?) {
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                super.onRangeStart(utteranceId, start, end, frame)

                word.value = "${word.value} ${response?.substring(start, end) ?: ""}"
            }

            override fun onStart(p0: String?) {
                word.value = ""
            }

            override fun onDone(p0: String?) {}

            override fun onError(p0: String?) {}
        })

        textToSpeech.speak(
            response,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "utterance_id"
        )
    }

    companion object {
        const val CHAT_GPT_API_KEY = Env.CUSTOM_OPENAI_API_KEY
    }
}