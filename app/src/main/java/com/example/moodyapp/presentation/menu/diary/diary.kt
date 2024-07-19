package com.example.moodyapp.presentation.menu.diary

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ScheduleSend
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import coil.compose.AsyncImage
import com.example.moodyapp.R
import com.example.moodyapp.data.manager.LocalUserMangerImpl
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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("ResourceType")
@Composable
fun Diary(dataStore: DataStore<Preferences>) {
    var apiKey by remember { mutableStateOf("") }
    val maxCharcont by rememberSaveable { mutableIntStateOf(300) }
    var numCharcont by rememberSaveable { mutableIntStateOf(0) }
    val maxChartitle by rememberSaveable { mutableIntStateOf(50) }
    var numChartitle by rememberSaveable { mutableIntStateOf(0) }
    val viewmodel = TextGeneratorViewModel()
    var isScrolleable by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val preferences = LocalUserMangerImpl(dataStore)
    val scope = rememberCoroutineScope()
    val title=preferences.getPreferenceStringFlow(LocalUserMangerImpl.TITLE_DIARY, "")
        .collectAsState(initial = "").value
    val content=preferences.getPreferenceStringFlow(LocalUserMangerImpl.CONTENT_DIARY_, "")
        .collectAsState(initial = "").value

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

        Log.e("MI INFO TAG 3",title+"\n"+content)

        var mydiarytexttitle by rememberSaveableWithVolatileInitialValue(title)

        var mydiarytextcontent by rememberSaveableWithVolatileInitialValue(content)

        Log.e("MI INFO TAG 4",mydiarytexttitle+"\n"+mydiarytextcontent)

        var responseText by remember {
            mutableStateOf("")
        }
        var imageURL by remember {
            mutableStateOf("")
        }
        //DAtabase
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        var username by remember {
            mutableStateOf("")
        }
        var isWrote by remember {
            mutableStateOf(true)
        }
        var emotion by remember {
            mutableStateOf("")
        }
        val myRef = database.getReference("users").child(auth.currentUser?.uid.toString())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    username = dataSnapshot.child("username").value.toString()
                    isWrote = dataSnapshot.child("emotions").child(getDate2()).exists().not()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("ERROR DATABASE", error.toString())
            }
        })

        if (!viewmodel.apiError) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CharacterTextField(
                    label = stringResource(id = R.string.titletext),
                    count = "$numChartitle/$maxChartitle",
                    isWrote = isWrote,
                    modifier = Modifier
                        .width(240.dp)
                        .padding(0.dp),
                    text = mydiarytexttitle  //title page
                ) {
                    if (it.length <= maxChartitle) {
                        mydiarytexttitle = it
                        numChartitle = it.length
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            Log.e("MI INFO TAG",mydiarytexttitle+"\n"+mydiarytextcontent)
                            preferences.updatePreferenceString(LocalUserMangerImpl.TITLE_DIARY, mydiarytexttitle)
                            preferences.updatePreferenceString(LocalUserMangerImpl.CONTENT_DIARY_, mydiarytextcontent)
                            Log.e("MI INFO TAG 2",mydiarytexttitle+"\n"+mydiarytextcontent)

                            Toast.makeText(context, R.string.SavedPage, Toast.LENGTH_SHORT).show()
                        }
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    modifier= Modifier.size(50.dp),  //avoid the oval shape
                    shape = CircleShape,
                    contentPadding = PaddingValues(10.dp),
                    enabled = isWrote,
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "save icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            CharacterContentTextField(
                label = stringResource(id = R.string.mydeardiary),
                count = "$numCharcont/$maxCharcont",
                isWrote = isWrote,
                text = mydiarytextcontent,

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
            description = "my action button for diary",
            enabled = isWrote,
        ) {
            shownEmotions = true
        }

        if (!isWrote) {
            Text(
                text = stringResource(id = R.string.isWrote),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        if (responseText.isNotEmpty()) {
            HorizontalDivider(
                modifier = Modifier.padding(0.dp, 30.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            if (isWrote) {
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
                viewmodel.processDiary(title = mydiarytexttitle,
                    content = mydiarytextcontent,
                    user = username,
                    emotion = emotion,
                    responseText = {
                        responseText = viewmodel.response
                    },
                    context = context,
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
        MyNoDiaryDialog(shown = shownDiary, onDismissRequest = {
            shownDiary = false
            emotion = ""
        }, onConfirmButton = {
            shownDiary = false
            emotion = ""
        })

        MyEmotionalDialog(shown = shownEmotions, onDismissRequest = {
            shownEmotions = false
            emotion = ""
        }, onConfirmButton = {
            if (emotion.isNotEmpty()) {
                shownEmotions = false
                shown = true
            }
        }) {
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
    emotion: (String) -> Unit
) {
    EmotionalDialog(
        shown = shown,
        onDismissRequest = { onDismissRequest() },
        confirmButton = { onConfirmButton() },
    ) { emotion(it) }
}

@Composable
private fun <T : Any> rememberSaveableWithVolatileInitialValue(
    initialValue: T
): MutableState<T> {
    return key(initialValue) {
        rememberSaveable {
            mutableStateOf(initialValue)
        }
    }
}

private fun getDate2(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val current = LocalDateTime.now().format(formatter).toString()
    return current
}
