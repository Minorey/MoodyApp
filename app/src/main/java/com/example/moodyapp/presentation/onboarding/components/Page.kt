package com.example.moodyapp.presentation.onboarding.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.moodyapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pagesList= listOf(
    Page(
        title = "Page1",
        description = LoremIpsum(100).toString(),
        image = R.drawable.onboardinghome1,
    ),
    Page(
        title = "Page2",
        description = LoremIpsum(100).toString(),
        image = R.drawable.onboardinghome1,
    ),
    Page(
        title = "Page3",
        description = LoremIpsum(100).toString(),
        image = R.drawable.onboardinghome1,
    ),
)