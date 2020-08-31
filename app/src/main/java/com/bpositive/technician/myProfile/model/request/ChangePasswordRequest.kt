package com.bpositive.technician.myProfile.model.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("new_password") val newPassword: String? = null,
    @SerializedName("technician_id") val technicianId: Int? = null
)