package com.bpositive.technician.myProfile.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("http_status")
	val httpStatus: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
