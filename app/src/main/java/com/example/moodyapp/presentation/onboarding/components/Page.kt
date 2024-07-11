package com.example.moodyapp.presentation.onboarding.components

import androidx.annotation.DrawableRes
import com.example.moodyapp.R

data class Page(
    val description: String,
    @DrawableRes val image: Int,
)

val pagesList = listOf(
    Page(
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc imperdiet congue sapien ut semper. Vestibulum rhoncus ac elit consequat efficitur. Nunc efficitur mauris eu enim interdum, sed dapibus metus sodales. In et mauris ut lacus maximus pharetra. Nunc vestibulum aliquam diam, sed lobortis tortor aliquet nec.",
        image = R.drawable.onboardinghome1,
    ),
    Page(
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc imperdiet congue sapien ut semper. Vestibulum rhoncus ac elit consequat efficitur. Nunc efficitur mauris eu enim interdum, sed dapibus metus sodales. In et mauris ut lacus maximus pharetra. Nunc vestibulum aliquam diam, sed lobortis tortor aliquet nec.",
        image = R.drawable.onboardinghome1,
    ),
    Page(
        description ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc imperdiet congue sapien ut semper. Vestibulum rhoncus ac elit consequat efficitur. Nunc efficitur mauris eu enim interdum, sed dapibus metus sodales. In et mauris ut lacus maximus pharetra. Nunc vestibulum aliquam diam, sed lobortis tortor aliquet nec.",
        image = R.drawable.onboardinghome1,
    ),
)