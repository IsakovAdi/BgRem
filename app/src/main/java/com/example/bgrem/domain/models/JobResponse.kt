package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class JobResponse(
    @SerializedName("id")val id: String,
    @SerializedName("duration")val duration: Int,
    @SerializedName("is_portrait")val is_portrait: Boolean,
    @SerializedName("source_url")val source_url: String,
    @SerializedName("thumbnail_url")val thumbnail_url: String,
    @SerializedName("video_height")val video_height: Int,
    @SerializedName("video_width")val video_width: Int
):Serializable