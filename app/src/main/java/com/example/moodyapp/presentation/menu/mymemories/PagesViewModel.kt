package com.example.moodyapp.presentation.menu.mymemories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PagesViewModel : ViewModel() {

    private var _pageList = MutableStateFlow<List<SinglePage>>((emptyList()))
    var pageList = _pageList.asStateFlow()
    private var _page = MutableStateFlow(SinglePage())
    var page = _page.asStateFlow()

    init {
        getPageList()
    }

    private fun getPageList() {
        val db = Firebase.firestore
        val ref =
            db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
                .collection("record")
        ref.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                _pageList.value = value.toObjects()
            }
        }
    }

    fun getPage(Date: String) {
        val db = Firebase.firestore
        val ref =
            db.collection("users").document(Firebase.auth.currentUser?.uid.toString())
                .collection("record").document(Date)
        ref.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    _page.value = documentSnapshot.toObject(SinglePage::class.java)!!
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

}