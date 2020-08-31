package com.bpositive.technician.myProfile.service

import com.bpositive.technician.myProfile.model.*
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.utils.ApiConstants
import com.bpositive.technician.utils.ApiConstants.INDEX
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MyProfileApi {

    @POST(INDEX)
    suspend fun getMyProfile(
        @Query("type") type: String = ApiConstants.GET_PROFILE,
        @Body profileRequest: ProfileRequest
    ): Response<ProfileResponse>

    @POST(INDEX)
    suspend fun updateMyProfile(
        @Query("type") type: String = ApiConstants.UPDATE_PROFILE,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @POST(INDEX)
    suspend fun changePassword(
        @Query("type") type: String = ApiConstants.CHANGE_PASSWORD,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

}