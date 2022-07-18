package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName

class ImageFile(
    @SerializedName("name") val name: String,
    @SerializedName("data") val data: ByteArray
)