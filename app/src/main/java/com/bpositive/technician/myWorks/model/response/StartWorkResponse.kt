package com.bpositive.technician.myWorks.model.response

import com.google.gson.annotations.SerializedName

data class StartWorkResponse(
    @SerializedName("details") val details: Details? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class Details(
    @SerializedName("job_id") val jobId: String? = null
)
