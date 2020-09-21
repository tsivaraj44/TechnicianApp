package com.bpositive.technician.login.service

import com.bpositive.technician.login.model.*
import com.bpositive.technician.utils.ApiConstants.GENERATE_OTP
import com.bpositive.technician.utils.ApiConstants.INDEX
import com.bpositive.technician.utils.ApiConstants.TECHNICAL_LOGIN
import com.bpositive.technician.utils.ApiConstants.VERIFY_OTP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {

    @POST(INDEX)
    suspend fun doLogin(
        @Query("type") type: String = TECHNICAL_LOGIN,
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST(INDEX)
    suspend fun generateOtp(
        @Query("type") type: String = GENERATE_OTP,
        @Body reqGenerateOtp: ReqGenerateOtp
    ): Response<GenerateOtpRes>

    @POST(INDEX)
    suspend fun verifyOtp(
        @Query("type") type: String = VERIFY_OTP,
        @Body reqVerifyOtp: ReqVerifyOtp
    ): Response<VerifyOtpRes>

}