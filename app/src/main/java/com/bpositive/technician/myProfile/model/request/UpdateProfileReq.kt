package com.bpositive.technician.myProfile.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileReq(
    @SerializedName("address") val address: String? = null,
    @SerializedName("pin_code") val pinCode: String? = null,
    @SerializedName("device_token") val deviceToken: String? = null,
    @SerializedName("technician_id") val technicianId: Int? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("device_type") val deviceType: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("email") val email: String? = null
)
