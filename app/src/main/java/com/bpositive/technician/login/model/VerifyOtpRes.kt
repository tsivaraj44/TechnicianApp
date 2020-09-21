package com.bpositive.technician.login.model

import com.google.gson.annotations.SerializedName

data class VerifyOtpRes(
    @SerializedName("details") val details: LoginDetails? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class LoginDetails(
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null,
    @SerializedName("auth_token") val authToken: String? = null,
    @SerializedName("email") val email: String? = null
)
