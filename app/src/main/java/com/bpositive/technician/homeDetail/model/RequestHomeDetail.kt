package com.bpositive.technician.homeDetail.model

import com.google.gson.annotations.SerializedName

data class RequestHomeDetail(
    @SerializedName("category_id") var categoryId: String? = null,
    @SerializedName("order_by") var orderBy: String? = null,
    var order: String? = null,
    val limit: String? = null,
    var offset: Int = 0,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("initiator_id") var initiatorId: String? = null
)