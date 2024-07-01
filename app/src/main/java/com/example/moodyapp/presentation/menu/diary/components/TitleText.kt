package com.example.moodyapp.presentation.menu.diary.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text:String){
    Text(text.uppercase(),fontSize = 16.sp, fontWeight = FontWeight.Bold)
}