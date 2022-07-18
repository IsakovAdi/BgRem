package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName

class BackgroundsResponse(
    @SerializedName("items")val items: List<BackgroundResponse>
)