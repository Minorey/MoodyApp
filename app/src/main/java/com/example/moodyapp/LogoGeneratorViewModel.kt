package com.example.moodyapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.example.moodyapp.util.Conf
import com.example.moodyapp.util.Env
import kotlinx.coroutines.launch

class LogoGeneratorViewModel : ViewModel() {
    private var openAI = OpenAI(token = Env.OPENAI_API_KEY, logging = LoggingConfig(LogLevel.All))

    var response: String by mutableStateOf("")
    var imageURI: String by mutableStateOf("")

    var loading: Boolean by mutableStateOf(false)
    //var recording: Boolean by mutableStateOf(false)

    var error: Boolean by mutableStateOf(false)
    var apiError: Boolean by mutableStateOf(false)

    //private var recorder: AudioRecorder? = null
    //private var audioFile: File? = null

    init {
        apiError = Env.OPENAI_API_KEY.isEmpty()
    }

    // API
    fun getCustomAPIKey(context: Context): String {
        val preferences = context.getSharedPreferences("", MODE_PRIVATE)
        val key = preferences.getString(Env.CUSTOM_OPENAI_API_KEY, "").toString()
        setCustomAPIKey(context, key)
        return key
    }

    fun setCustomAPIKey(context: Context, key: String) {
        openAI = OpenAI(token = key, logging = LoggingConfig(LogLevel.All))
        apiError = key.isEmpty()

        context.getSharedPreferences("", MODE_PRIVATE).edit().apply {
            putString(Env.CUSTOM_OPENAI_API_KEY, key)
            apply()
        }
    }

    //CartaDiario
    @OptIn(BetaOpenAI::class)
    fun processDiary(
        title: String,
        content: String,
        user: String,
        responseText: () -> Unit,
        imageURL: (String) -> Unit,
    ) =
        viewModelScope.launch {

            startLoading()
            try {
                val completion: ChatCompletion =
                    openAI.chatCompletion(
                        ChatCompletionRequest(
                            model = ModelId(Conf.GPT_MODEL),
                            messages = listOf(
                                ChatMessage(
                                    role = ChatRole.System,
                                    content = "Eres una llama(el animal), llamada Moody, que trabaja de asistente de psicología"
                                ),
                                ChatMessage(
                                    role = ChatRole.User,
                                    content = "Sabiendo que el nombre de la persona es $user, brinda un consejo detallado de apoyo emocional de manera natural a un joven, basándote en el siguiente título y contenido:\nTítulo:$title\nContenido:$content, al final coloca el nombre de la persona que te mandé"
                                )
                            )
                        )
                    )
                response = completion.choices.first().message.content.toString()
                createInfoSummary(response) {
                    imageURI = it
                    imageURL(it)
                }
                responseText()
            } catch (e: Exception) {
                Log.e("ERROR API GPT", e.toString())
            } finally {
                endLoading()
            }
        }

//    // Audio
//    fun recordAudio(context: Context) {
//        if (recording) {
//            recording = false
//            recorder?.stop()
//            loadInfo(audioFile)
//        } else {
//            if (recorder == null) {
//                recorder = AudioRecorder(context)
//            }
//            File(context.cacheDir, Conf.AUDIO_FILE).also {
//                recorder?.record(it)
//                audioFile = it
//                recording = true
//            }
//        }
//    }

    // Resumen
    @OptIn(BetaOpenAI::class)
    fun createInfoSummary(responseText: String, imageURL: (String) -> Unit) =
        viewModelScope.launch {
            startLoading()

            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId(Conf.GPT_MODEL),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "Eres un resumidor texto de manera extremadamente concisa y eficaz"
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = "Lista las palabras clave del siguiente texto: $responseText"
                    )
                )
            )
            generateLogo(
                openAI.chatCompletion(chatCompletionRequest).choices.first().message.content.toString(),
            ) {
                imageURI = it
                imageURL(it)
            }

            endLoading()
        }

    // Generación de img e edit en base image and mask
    @OptIn(BetaOpenAI::class)
    fun generateLogo(
        info: String,
        imageURL: (String) -> Unit,
    ) = viewModelScope.launch {

        startLoading()

        try {
            var prompt = "Cute draw of a baby llama with white background and with no text "
            if (info.isNotEmpty()) {
                prompt += "with the following information:  ${info.trim()}"
            }

            val images = openAI.imageURL( // or openAI.imageJSON
                creation = ImageCreation(
                    prompt = prompt,
                    model = ModelId(Conf.GPT_MODEL_IMAGE),
                    n = 1,
                    size = ImageSize.is1024x1024
                )
            )

            imageURL(images.first().url)

        } catch (e: Exception) {
            println(e)
            error = true
        } finally {

            endLoading()
        }
    }

//    // Transcripción
//    @OptIn(BetaOpenAI::class)
//    private fun loadInfo(file: File?) = viewModelScope.launch {
//
//        file?.source()?.let {
//
//            startLoading()
//
//            val transcriptionRequest = TranscriptionRequest(
//                audio = FileSource(name = Conf.AUDIO_FILE, source = it),
//                model = ModelId(Conf.WHISPER_MODEL)
//            )
//
//            val transcription = openAI.transcription(transcriptionRequest)
//
//            info = transcription.text
//
//            endLoading()
//        }
//    }

    // Loading

    private fun startLoading() {
        loading = true
        error = false
    }

    private fun endLoading() {
        loading = false
    }

}