package com.example.moodyapp.presentation.menu.diary

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ScheduleSend
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moodyapp.LogoGeneratorViewModel
import com.example.moodyapp.R
import com.example.moodyapp.presentation.common.SimpleAlertDialog
import com.example.moodyapp.presentation.menu.diary.components.ActionButton
import com.example.moodyapp.presentation.menu.diary.components.CharacterContentTextField
import com.example.moodyapp.presentation.menu.diary.components.CharacterTextField
import com.example.moodyapp.presentation.menu.diary.components.EmotionalDialog
import com.example.moodyapp.presentation.menu.diary.components.NoDiaryDialog
import com.example.moodyapp.presentation.menu.diary.content.APIKeyRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@SuppressLint("ResourceType")
@Composable
fun Diary() {
    var apiKey by remember { mutableStateOf("") }
    var maxCharcont by remember { mutableStateOf(400) }
    var numCharcont by remember { mutableStateOf(0) }
    var maxChartitle by remember { mutableStateOf(100) }
    var numChartitle by remember { mutableStateOf(0) }
    val viewmodel = LogoGeneratorViewModel()
    var isScrolleable by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // API Key
    APIKeyRow(context, viewmodel, apiKey) {
        apiKey = it
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(), enabled = isScrolleable)
            .padding(40.dp, 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var shown by remember {
            mutableStateOf(false)
        }
        var shownDiary by remember {
            mutableStateOf(false)
        }
        var shownEmotions by remember {
            mutableStateOf(false)
        }
        var mydiarytexttitle by remember {
            mutableStateOf("")
        }
        var mydiarytextcontent by remember {
            mutableStateOf("")
        }
        var responseText by remember {
            mutableStateOf("")
        }
        var imageURL by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        //DAtabase
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        var username by remember {
            mutableStateOf("")
        }
        var emotion by remember {
            mutableStateOf("")
        }
        val myRef = database.getReference("users").child(auth.currentUser?.uid.toString())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    username = dataSnapshot.child("username").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("ERROR DATABASE", error.toString())
            }
        })


        if (!viewmodel.apiError) {
            CharacterTextField(
                label = stringResource(id = R.string.titletext),
                count = "$numChartitle/$maxChartitle",
                text = mydiarytexttitle
            ) {
                if (it.length <= maxChartitle) {
                    mydiarytexttitle = it
                    numChartitle = it.length
                }
            }
            CharacterContentTextField(
                label = stringResource(id = R.string.mydeardiary),
                count = "$numCharcont/$maxCharcont",
                text = mydiarytextcontent
            ) {
                if (it.length <= maxCharcont) {
                    mydiarytextcontent = it
                    numCharcont = it.length
                }
            }
        }

        ActionButton(
            text = stringResource(id = R.string.diarybtn),
            icon = Icons.AutoMirrored.Filled.Send,
            description = "my action button for diary"
        ) {
            shownEmotions = true
        }
        if (responseText.isNotEmpty()) {
            HorizontalDivider(
                modifier = Modifier.padding(0.dp, 30.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            val painter = painterResource(id = R.raw.image)
            Image(
                painter = painter,
                contentDescription = "happy llama",
                modifier = Modifier
                    .size(180.dp)
                    .padding(0.dp, 30.dp, 0.dp, 0.dp),

                contentScale = ContentScale.Fit,

                )
        }
        Text(text = responseText)
        AsyncImage(
            model = imageURL,
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth(),
        )

        MyConfirmDialog(shown = shown, onDismissRequest = {
            shown = false
            emotion = ""
        }, onConfirmButton = {
            if (mydiarytexttitle.isNotEmpty() && mydiarytextcontent.isNotEmpty() && emotion.isNotEmpty()) {
                viewmodel.processDiary(
                    title = mydiarytexttitle,
                    content = mydiarytextcontent,
                    user = username,
                    emotion = emotion,
                    responseText = {
                        responseText = viewmodel.response
                    },
                    imageURL = {
                        imageURL = it
                    })
                shown = false
                isScrolleable = true
                emotion = ""
            } else {
                shown = false
                shownDiary = true
                emotion = ""
            }
        })
        MyNoDiaryDialog(
            shown = shownDiary,
            onDismissRequest = {
                shownDiary = false
                emotion = ""
            },
            onConfirmButton = {
                shownDiary = false
                emotion = ""
            }
        )

        MyEmotionalDialog(
            shown = shownEmotions,
            onDismissRequest = {
                shownEmotions = false
                emotion = ""
            },
            onConfirmButton = {
                if (emotion.isNotEmpty()) {
                    shownEmotions = false
                    shown = true
                }
            }
        ) {
            if (it.isNotEmpty()) {
                emotion = it
            }
        }

    }

}

@Composable
private fun MyConfirmDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    SimpleAlertDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
        title = stringResource(R.string.areYouSureDialogTitle),
        description = stringResource(R.string.areYouSureDialogContent),
        icondialog = Icons.AutoMirrored.Filled.ScheduleSend
    )
}

@Composable
private fun MyNoDiaryDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    NoDiaryDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
        title = stringResource(R.string.noDiaryDialogTitle),
        content = stringResource(R.string.noDiaryDialogContent),
    )
}

@Composable
private fun MyEmotionalDialog(
    shown: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
    Emotion: (String) -> Unit
) {
    EmotionalDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
    ) { Emotion(it) }
}
