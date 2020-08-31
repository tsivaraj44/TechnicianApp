package com.bpositive.technician.myProfile.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("details") val details: ProfileDetails? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class ProfileDetails(
    @SerializedName("address") val address: String? = null,
    @SerializedName("pin_code") val pinCode: String? = null,
    @SerializedName("device_token") val deviceToken: String? = null,
    @SerializedName("technician_id") val technicianId: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("device_type") val deviceType: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("email") val email: String? = null
)
