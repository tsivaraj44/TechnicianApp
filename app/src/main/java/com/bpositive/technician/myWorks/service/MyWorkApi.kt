package com.bpositive.technician.myWorks.service

import com.bpositive.technician.home.model.ResSettlement
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.model.response.StartWorkResponse
import com.bpositive.technician.utils.ApiConstants.ALL_BOOKING_LIST
import com.bpositive.technician.utils.ApiConstants.INDEX
import com.bpositive.technician.utils.ApiConstants.MOVE_COMPLETED
import com.bpositive.technician.utils.ApiConstants.MOVE_TO_PENDING
import com.bpositive.technician.utils.ApiConstants.SETTLEMENT
import com.bpositive.technician.utils.ApiConstants.START_WORK
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MyWorkApi {

    @POST(INDEX)
    suspend fun getMyWork(
        @Query("type") type: String = ALL_BOOKING_LIST,
        @Body myWorkRequest: MyWorkRequest
    ): Response<MyWorkResponse>

    @POST(INDEX)
    suspend fun startWork(
        @Query("type") type: String = START_WORK,
        @Body startWorkRequest: StartWorkRequest
    ): Response<StartWorkResponse>

    @POST(INDEX)
    suspend fun moveToPending(
        @Query("type") type: String = MOVE_TO_PENDING,
        @Body moveToPendingReq: MoveToPendingReq
    ): Response<StartWorkResponse>

    @Multipart
    @POST(INDEX)
    suspend fun completeWork(
        @Part("technician_id") technicianId: RequestBody,
        @Part("job_id") jobId: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("comments") comments: RequestBody,
        @Part fileJobAttachment1: MultipartBody.Part? = null,
        @Part fileJobAttachment2: MultipartBody.Part? = null,
        @Part fileJobAttachment3: MultipartBody.Part? = null,
        @Query("type") type: String = MOVE_COMPLETED
    ): Response<StartWorkResponse>

    @Multipart
    @POST("/imagefolder/index")
    suspend fun uploadVideoToServer(@Part video: MultipartBody.Part)

    @POST(INDEX)
    suspend fun getSettlement(
        @Query("type") type: String = SETTLEMENT,
        @Body profileRequest: ProfileRequest
    ): Response<ResSettlement>

}