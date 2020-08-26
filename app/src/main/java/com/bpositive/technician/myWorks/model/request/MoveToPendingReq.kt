package com.bpositive.technician.myWorks.model.request

import com.google.gson.annotations.SerializedName

data class MoveToPendingReq(
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("comments") val comments: String? = null,
    @SerializedName("job_id") val jobId: Int? = null,
    @SerializedName("technician_id") val technicianId: Int? = null
)
