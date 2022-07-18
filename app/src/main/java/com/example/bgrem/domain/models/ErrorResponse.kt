package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("message") val message: String
)