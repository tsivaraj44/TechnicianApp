package com.bpositive.technician.login.model

import com.google.gson.annotations.SerializedName

data class ReqVerifyOtp(
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("otp") val otp: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null,
    @SerializedName("device_token") val deviceToken: String? = null
)
