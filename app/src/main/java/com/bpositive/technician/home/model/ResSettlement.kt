package com.bpositive.technician.home.model

import com.google.gson.annotations.SerializedName

data class ResSettlement(
    @SerializedName("total_amount") val totalAmount: Int? = null,
    @SerializedName("given_amount") val givenAmount: Int? = null,
    @SerializedName("details") val settlementList: List<Settlement?>? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class Settlement(
    @SerializedName("area_name") val areaName: Any? = null,
    @SerializedName("reason") val reason: String? = null,
    @SerializedName("category_name") val categoryName: String? = null,
    @SerializedName("comments") val comments: Any? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("customer_number") val customerNumber: String? = null,
    @SerializedName("visit_time") val visitTime: String? = null,
    @SerializedName("payment_status") val paymentStatus: String? = null,
    @SerializedName("assigned_technician") val assignedTechnician: String? = null,
    @SerializedName("brand_name") val brandName: String? = null,
    @SerializedName("device_type") val deviceType: String? = null,
    @SerializedName("travel_status") val travelStatus: String? = null,
    @SerializedName("assigned_technician_name") val assignedTechnicianName: Any? = null,
    @SerializedName("category_id") val categoryId: String? = null,
    @SerializedName("model_name") val modelName: String? = null,
    @SerializedName("total_amount") val totalAmount: String? = null,
    @SerializedName("device_token") val deviceToken: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("customer_name") val customerName: String? = null,
    @SerializedName("customer_id") val customerId: String? = null,
    @SerializedName("landmark") val landmark: String? = null,
    @SerializedName("create_date") val createDate: String? = null,
    @SerializedName("visit_date") val visitDate: String? = null,
    @SerializedName("status") val status: String? = null
)
