package com.bpositive.technician.home.service

import com.bpositive.technician.home.model.HomeDomainListResponse
import com.bpositive.technician.utils.BaseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    // http://192.168.1.104:1041/
    // coreapiv1?type=getdomains&lang=en


    @GET("index")
    fun getHomeDomainList(
        @Query("type") type: String,
        @Query("lang") lang: String = BaseData.DEFAULT_LANG_CODE
    ): Call<HomeDomainListResponse>


}