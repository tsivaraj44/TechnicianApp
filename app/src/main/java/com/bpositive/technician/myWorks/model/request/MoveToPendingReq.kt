package com.bpositive.technician.myWorks.model.request

import com.google.gson.annotations.SerializedName

data class MoveToPendingReq(
    @SerializedName("amount") val amount: Double? = 0.0,
    @SerializedName("comments") val comments: String? = "",
    @SerializedName("job_id") val jobId: Int? = 0,
    @SerializedName("technician_id") val technicianId: Int? = 0
)
