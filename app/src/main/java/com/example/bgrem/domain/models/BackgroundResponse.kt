package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BackgroundResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("group") val group: String? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("file_url") val file_url: String? = null,
    @SerializedName("thumbnail_url") val thumbnail_url: String? = null,
    @SerializedName("poster_url") val poster_url: String? = null
) : Serializable
