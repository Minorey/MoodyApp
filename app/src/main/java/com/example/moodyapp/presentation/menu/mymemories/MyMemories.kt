package com.example.moodyapp.presentation.menu.mymemories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moodyapp.R
import com.example.moodyapp.presentation.nvgraph.Route
import java.io.File

@Composable
fun MyMemories(viewModel: PagesViewModel = viewModel(), navController: NavController) {
    val pages by viewModel.pageList.collectAsStateWithLifecycle()

    if (pages.isNotEmpty()) {
        Scaffold { paddingValues ->
            LazyColumn(
                contentPadding = PaddingValues(30.dp, 50.dp),
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(pages) { page ->
                    val myemo: Int =
                        if (page.emotion.toString() == "Angry" || page.emotion.toString() == "Molesto") {
                            R.drawable.angry
                        } else if (page.emotion.toString() == "Sad" || page.emotion.toString() == "Triste") {
                            R.drawable.sad
                        } else if (page.emotion.toString() == "Worried" || page.emotion.toString() == "Preocupado") {
                            R.drawable.worry
                        } else if (page.emotion.toString() == "Surprised" || page.emotion.toString() == "Sorprendido") {
                            R.drawable.surprise
                        } else R.drawable.happy

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .height(70.dp)
                            .padding(10.dp, 0.dp)
                            .clickable {
                                navController.navigate(
                                    Route.MyPageScreen.route + "/${page.date}"
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            painter = painterResource(id = myemo),
                            contentDescription = "emotion",
                            modifier = Modifier.size(40.dp)
                                .clip(shape = RoundedCornerShape(5.dp))
                        )
                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 20.dp, end = 5.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = page.title.toString(),
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.tertiary,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Text(
                                text = page.date.toString(),
                                fontStyle = MaterialTheme.typography.bodySmall.fontStyle,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        val imgFile =
                            File("/storage/emulated/0/Pictures/Moody/${page.image.toString()}.jpg")
                        var imgBitmap: Bitmap? = null
                        if (imgFile.exists()) {
                            imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                        }
                        Image(
                            painter = rememberAsyncImagePainter(model = imgBitmap),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = RoundedCornerShape(5.dp))
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(40.dp, 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.llamaread),
                contentDescription = "empty",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 30.dp)
            )
            Row {
                Text(text = stringResource(id = R.string.iNotWrite))
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "write icon",
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }
}