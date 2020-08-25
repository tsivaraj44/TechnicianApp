package com.bpositive.technician.homeDetail.service.repository

import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.homeDetail.model.RequestHomeDetail
import com.bpositive.technician.homeDetail.model.ResponseHomeDetail
import com.bpositive.technician.homeDetail.service.HomeDetailService
import com.bpositive.technician.utils.*
import com.bpositive.technician.utils.BaseData.BASE_URL
import kotlinx.coroutines.*

class HomeDetailRepository {

    val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)

    private val homeDetailService: HomeDetailService by lazy {
        NetworkManager.baseURL(BASE_URL).serviceClass(HomeDetailService::class.java)
            .create<HomeDetailService>()
    }

    fun getHomeDetailResponse(
        requestHomeDetail: RequestHomeDetail,
        onSuccess: OnSuccess<ResponseHomeDetail>,
        onError: OnError<String>
    ) =
        backgroundScope.launch {
            when (val result: Result<ResponseHomeDetail> =
                homeDetailService.callGetHomeDetailApi(requestHomeDetail, APITypes.casesList).awaitResult()) {
                is Result.Ok -> withContext(Dispatchers.Main) {
                    if (result.value.status!!.isSuccess())
                        onSuccess(result.value)
                    else
                        onError(result.value.message.toString())
                }
                is Result.Error -> withContext(Dispatchers.Main) { onError(result.exception.localizedMessage) }
                is Result.Exception -> withContext(Dispatchers.Main) { onError(result.exception.localizedMessage) }
            }
        }

}