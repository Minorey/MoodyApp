package com.example.moodyapp.presentation.menu.diary

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.moodyapp.downloader.AndroidDownloader
import com.example.moodyapp.util.Conf
import com.example.moodyapp.util.Env
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TextGeneratorViewModel : ViewModel() {
    private var openAI = OpenAI(token = Env.OPENAI_API_KEY, logging = LoggingConfig(LogLevel.All))

    var response: String by mutableStateOf("")
    private var imageURI: String by mutableStateOf("")

    private var loading: Boolean by mutableStateOf(false)

    private var error: Boolean by mutableStateOf(false)
    var apiError: Boolean by mutableStateOf(false)

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
    fun processDiary(
        title: String,
        content: String,
        emotion: String,
        user: String,
        context: Context,
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
                                    content = "Eres una llama(el animal), llamada Moody, que trabaja de experto asistente de psicología"
                                ),
                                ChatMessage(
                                    role = ChatRole.User,
                                    content = "Sabiendo que el nombre de la persona es $user y que está $emotion, brinda un consejo de apoyo emocional de manera natural a un joven, basándote en el siguiente título y contenido:\nTítulo:$title\nContenido:$content, al final coloca el nombre de la persona que te mandé [Coloca Emojis]"
                                )
                            )
                        )
                    )
                response = completion.choices.first().message.content.toString()
                createInfoSummary(title = title, content=content, emotion=emotion, context = context) {
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

    // Resumen
    private fun createInfoSummary(
        title: String,
        content: String,
        emotion: String,
        context: Context,
        imageURL: (String) -> Unit
    ) =
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
                        content = "Lista las palabras clave del siguiente texto: $response"
                    )
                )
            )

            val info= chatCompletionRequest.messages.first().content.toString()

            generateLogo(
                title = title,
                content = content,
                emotion = emotion,
                info= info,
                context = context
            ) {
                imageURI = it
                imageURL(it)
            }

            endLoading()
        }

    // Generación de img e edit en base image and mask
    private fun generateLogo(
        title: String,
        content: String,
        emotion: String,
        info: String = "",
        context: Context,
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

            val downloader= AndroidDownloader(context)
            downloader.downloadFile(images.first().url,getDate1())
            val file = Uri.fromFile(File("/storage/emulated/0/Pictures/Moody/${getDate1()}.jpg"))
            val imgref =
                Firebase.storage.reference.child("users/${Firebase.auth.uid.toString()}/imagePages/${file.lastPathSegment}")
            imgref.putFile(file)

            guardarDatos(title, content, response, getDate1(), emotion)


        } catch (e: Exception) {
            println(e)
            error = true
        } finally {
            endLoading()
        }

    }

    private fun guardarDatos(
        title: String,
        content: String,
        response: String,
        image: String,
        emotion: String
    ) {
        val user = Firebase.auth.uid.toString()
        val databaseReal = Firebase.database.reference.child("users").child(user)
            .child("emotions")
        val databaseFire =
            Firebase.firestore.collection("users").document(user)
                .collection("record").document(getDate1())//format dd-mm-yyyy
        val page = hashMapOf(
            "date" to getDate1(),
            "title" to title,
            "content" to content,
            "response" to response,
            "image" to image,
            "emotion" to emotion
        )

        databaseFire.set(page)
        databaseReal.child(getDate2()).child("emotion").setValue(emotion)

    }

    private fun getDate1(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val current = LocalDateTime.now().format(formatter).toString()
        return current
    }

    private fun getDate2(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val current = LocalDateTime.now().format(formatter).toString()
        return current
    }

    private fun startLoading() {
        loading = true
        error = false
    }

    private fun endLoading() {
        loading = false
    }

}