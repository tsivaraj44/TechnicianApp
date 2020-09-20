package com.bpositive.technician.home.service.repository

import com.bpositive.technician.home.model.ResSettlement
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.service.MyWorkApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val api: MyWorkApi) : IHomeRepository {

    override suspend fun getSettlement(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ResSettlement>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.getSettlement(profileRequest = profileRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status!!)
                            withContext(Dispatchers.Main) { onSuccess(it) }
                        else
                            withContext(Dispatchers.Main) { onError(it.message.toString()) }
                    }
                } else
                    withContext(Dispatchers.Main) { onError(response.message().toString()) }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onError(e.toString()) }
            }
        }
    }

}

interface IHomeRepository {
    suspend fun getSettlement(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ResSettlement>,
        onError: OnError<String>
    )

    companion object Factory {
        fun getInstance(api: MyWorkApi): IHomeRepository = HomeRepository(api)
    }
}