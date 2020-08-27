package com.bpositive.technician.login.service

import com.bpositive.technician.login.model.LoginRequest
import com.bpositive.technician.login.model.LoginResponse
import com.bpositive.technician.utils.ApiConstants.INDEX
import com.bpositive.technician.utils.ApiConstants.TECHNICAL_LOGIN
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

}