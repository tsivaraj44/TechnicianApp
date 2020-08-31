package com.bpositive.technician.myProfile.model

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    @SerializedName("technician_id") val technicianId: Int? = null
)
