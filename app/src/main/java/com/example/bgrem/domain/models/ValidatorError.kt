package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName

class ValidatorError(
    @SerializedName("loc") val loc: List<String>,
    @SerializedName("msg") val msg: String,
    @SerializedName("type") val type: String
)