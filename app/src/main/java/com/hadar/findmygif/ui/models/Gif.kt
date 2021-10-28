package com.hadar.findmygif.ui.models

import androidx.annotation.Nullable

data class Gif constructor(
    @field:Nullable var gifTitle: String,
    val imageUrl: String
)