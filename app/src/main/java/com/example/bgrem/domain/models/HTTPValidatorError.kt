package com.example.bgrem.domain.models

import com.google.gson.annotations.SerializedName

class HTTPValidatorError(
    @SerializedName("detail") val detail: List<ValidatorError>
)