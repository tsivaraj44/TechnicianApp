package com.bpositive.technician.login.model

import com.google.gson.annotations.SerializedName

data class GenerateOtpRes(
    @SerializedName("otp") val otp: Int? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)
