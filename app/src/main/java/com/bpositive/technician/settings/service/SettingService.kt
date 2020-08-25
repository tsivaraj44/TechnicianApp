package com.bpositive.technician.settings.service

import com.bpositive.technician.settings.model.LanguageModel
import com.bpositive.technician.settings.model.SettingMenus
import com.bpositive.technician.settings.model.SettingsRequest
import com.bpositive.technician.utils.BaseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SettingService {

    @GET("index")
    fun callLanguageList(
        @Query("type") type: String,
        @Query("lang") lang: String = BaseData.DEFAULT_LANG_CODE
    ): Call<LanguageModel>

    @POST("index")
    fun getSettingsDomainList(
        @Query("type") type: String,
        @Query("lang") lang: String = BaseData.DEFAULT_LANG_CODE,
        @Body settingReq: SettingsRequest
    ): Call<SettingMenus>

}