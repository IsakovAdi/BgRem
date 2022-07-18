package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class TaskResponse(
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: String?,
    @SerializedName("progress") val progress: Int,
    @SerializedName("source_url") val source_url: String,
    @SerializedName("result_url") val result_url: String?,
    @SerializedName("bg_group") val bg_group: String,
    @SerializedName("bg_url") val bg_url: String,
    @SerializedName("download_url") val download_url: String
):Serializable