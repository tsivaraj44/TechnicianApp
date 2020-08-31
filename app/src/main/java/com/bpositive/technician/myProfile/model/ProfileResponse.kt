package com.bpositive.technician.myProfile.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("details") val details: Details? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class Details(
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null,
    @SerializedName("pincode") val pincode: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("auth_token") val authToken: String? = null
)
