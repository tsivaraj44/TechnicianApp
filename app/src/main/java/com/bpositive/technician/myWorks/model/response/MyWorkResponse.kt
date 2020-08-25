package com.bpositive.technician.myWorks.model.response

import com.google.gson.annotations.SerializedName

data class MyWorkResponse(
    val details: List<Works?>? = null,
    val httpStatus: Int? = null,
    val message: String? = null,
    val status: Boolean? = null
)

data class Works(
    val reason: String? = null,
    @SerializedName("category_name") val categoryName: String? = null,
    @SerializedName("customer_number") val customerNumber: String? = null,
    @SerializedName("visit_time") val visitTime: String? = null,
    @SerializedName("technician_id") val technicianId: String? = null,
    @SerializedName("brand_name") val brandName: String? = null,
    @SerializedName("model_id") val modelId: String? = null,
    @SerializedName("brand_id") val brandId: String? = null,
    @SerializedName("category_image") val categoryImage: String? = null,
    @SerializedName("model_name") val modelName: String? = null,
    @SerializedName("category_id") val categoryId: String? = null,
    @SerializedName("job_id") val jobId: String? = null,
    @SerializedName("category_name") val customerName: String? = null,
    @SerializedName("customer_id") val customerId: String? = null,
    @SerializedName("visit_date") val visitDate: String? = null
)

