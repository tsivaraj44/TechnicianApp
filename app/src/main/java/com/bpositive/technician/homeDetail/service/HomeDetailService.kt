package com.bpositive.technician.homeDetail.service

import com.bpositive.technician.homeDetail.model.RequestHomeDetail
import com.bpositive.technician.homeDetail.model.ResponseHomeDetail
import com.bpositive.technician.utils.ApiConstants.INDEX
import com.bpositive.technician.utils.BaseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeDetailService {

    @POST(INDEX)
    fun callGetHomeDetailApi(
        @Body requestHomeDetail: RequestHomeDetail,
        @Query("type") type: String,
        @Query("lang") lang: String = BaseData.DEFAULT_LANG_CODE
    ): Call<ResponseHomeDetail>

}