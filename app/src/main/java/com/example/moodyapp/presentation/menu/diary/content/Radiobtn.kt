package com.example.moodyapp.presentation.menu.diary.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moodyapp.R

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String,
    val painter: Painter,
)

@Composable
fun RadioButtons() {

    val angryText = stringResource(id = R.string.emoAngry)
    val angryImage = painterResource(id = R.drawable.angry)
    val surpriseText = stringResource(id = R.string.emoSurprised)
    val surpriseImage = painterResource(id = R.drawable.surprise)
    val happyText = stringResource(id = R.string.emoHappy)
    val happyImage = painterResource(id = R.drawable.happy)
    val worryText = stringResource(id = R.string.emoWorried)
    val worryImage = painterResource(id = R.drawable.worry)
    val sadText = stringResource(id = R.string.emoSad)
    val sadImage = painterResource(id = R.drawable.sad)

    var myEmotion = remember { mutableStateOf(happyText) }

    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text = angryText,
                painter = angryImage
            ),
            ToggleableInfo(
                isChecked = false,
                text = surpriseText,
                painter = surpriseImage
            ),
            ToggleableInfo(
                isChecked = true,
                text = happyText,
                painter = happyImage
            ),
            ToggleableInfo(
                isChecked = false,
                text = worryText,
                painter = worryImage
            ),
            ToggleableInfo(
                isChecked = false,
                text = sadText,
                painter = sadImage
            ),
        )
    }



    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.iFeel),
                style = MaterialTheme.typography.displaySmall
            )
        }
        Row() {
            radioButtons.forEachIndexed { index, info ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            radioButtons.replaceAll {
                                it.copy(
                                    isChecked = it.text == info.text
                                )
                            }
                            myEmotion.value = info.text
                        }
                        .padding(0.dp, 5.dp)
                        .width(55.dp)
                        .height(80.dp)
                        .padding(vertical = 10.dp)
                ) {
                    Image(
                        painter = info.painter,
                        contentDescription = info.text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                    )
                    RadioButton(
                        selected = info.isChecked,
                        onClick = {
                            radioButtons.replaceAll {
                                it.copy(
                                    isChecked = it.text == info.text
                                )
                            }
                            myEmotion.value = info.text
                        },
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }
        }
        Row {
            Text(
                text = myEmotion.value,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}