package com.bpositive.technician.myProfile.service

import com.bpositive.technician.myProfile.model.ChangePasswordResponse
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myProfile.model.ProfileResponse
import com.bpositive.technician.myProfile.model.UpdateProfileResponse
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.myProfile.model.request.UpdateProfileReq
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
        @Body updateProfileReq: UpdateProfileReq
    ): Response<UpdateProfileResponse>

    @POST(INDEX)
    suspend fun changePassword(
        @Query("type") type: String = ApiConstants.CHANGE_PASSWORD,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

}