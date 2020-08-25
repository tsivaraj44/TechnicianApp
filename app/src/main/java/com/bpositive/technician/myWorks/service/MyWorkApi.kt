package com.bpositive.technician.myWorks.service

import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.model.response.StartWorkResponse
import com.bpositive.technician.utils.ApiConstants.ALL_BOOKING_LIST
import com.bpositive.technician.utils.ApiConstants.INDEX
import com.bpositive.technician.utils.ApiConstants.START_WORK
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

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

}