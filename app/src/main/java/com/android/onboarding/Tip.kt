package com.android.onboarding

import androidx.annotation.DrawableRes

data class Tip (
    val title: String,
    val description: String,
    @DrawableRes val logo: Int,
    @DrawableRes val background: Int
)