package com.bpositive.technician.myWorks.model.request

import com.google.gson.annotations.SerializedName

data class MyWorkRequest(
    @SerializedName("technician_id")
    val technicianId: Int? = null,
    @SerializedName("travel_status")
    val travelStatus: Int? = null
)