package com.bpositive.technician.login.model

import com.google.gson.annotations.SerializedName

data class ReqGenerateOtp(
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("device_id") val deviceId: String? = null,
    @SerializedName("device_token") val deviceToken: String? = null,
    @SerializedName("device_type") val deviceType: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null
)
