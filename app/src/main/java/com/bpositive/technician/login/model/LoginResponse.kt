package com.bpositive.technician.login.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_details") val userDetails: UserDetails? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)

data class UserDetails(
    @SerializedName("address") val address: String? = null,
    @SerializedName("pin_code") val pinCode: String? = null,
    @SerializedName("technician_id") val technicianId: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null,
    @SerializedName("auth_token") val authToken: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("logged_in_time") val loggedInTime: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("status") val status: String? = null
)
