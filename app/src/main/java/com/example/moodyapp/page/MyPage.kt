package com.example.moodyapp.page

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.moodyapp.presentation.menu.mymemories.SinglePage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun MyPage(mydate: String) {

    val db = Firebase.firestore
    var mydata by remember {
        mutableStateOf(SinglePage())
    }
    val ref =
        db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
            .collection("record").document(mydate)

    val context= LocalContext.current

    (context as? Activity)?.requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    ///////////////////////////////////////////////////////////////////////
    ref.get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null) {
                mydata = documentSnapshot.toObject(SinglePage::class.java)!!
                Log.d("DATA SUCH DOCUMENT", mydata.toString())
                Log.d("DATE SUCH DOCUMENT", mydate)
            } else {
                Log.d("DATA NO SUCH DOCUMENT", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("DATA GET FAILED TO CONECT", "get failed with ", exception)
        }

    val imagePath="users/${Firebase.auth.uid.toString()}/imagePages/${mydate}.jpg"

    val coroutineScope = rememberCoroutineScope()
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(imagePath) {
        coroutineScope.launch {
            imageUrl = getImageUrl(imagePath)
        }
    }

    ///////////////////////////////////////////////////////////////////////

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp,60.dp,40.dp,40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = mydata.title.toString(),
            color = MaterialTheme.colorScheme.tertiary,
            fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = Color.Transparent
        )

        Text(text = mydata.content.toString(), color = MaterialTheme.colorScheme.tertiary)

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = Color.Transparent
        )
        imageUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 40.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Text(text = mydata.response.toString(), color = MaterialTheme.colorScheme.tertiary)
    }


}


suspend fun getImageUrl(imagePath: String): String? {
    return try {
        val storageReference = Firebase.storage.reference.child(imagePath)
        storageReference.downloadUrl.await().toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}