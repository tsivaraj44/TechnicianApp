package com.bpositive.technician.myProfile.service

import com.bpositive.technician.myProfile.model.*
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.myProfile.model.request.UpdateProfileReq
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyProfileRepository(
    private val api: MyProfileApi
) : IMyProfileRepository {

    override suspend fun getMyProfile(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ProfileResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.getMyProfile(profileRequest = profileRequest)
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

    override suspend fun updateMyProfile(
        updateProfileReq: UpdateProfileReq,
        onSuccess: OnSuccess<UpdateProfileResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.updateMyProfile(updateProfileReq = updateProfileReq)
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

    override suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        onSuccess: OnSuccess<ChangePasswordResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.changePassword(changePasswordRequest = changePasswordRequest)
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

interface IMyProfileRepository {
    suspend fun getMyProfile(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ProfileResponse>,
        onError: OnError<String>
    )

    suspend fun updateMyProfile(
        updateProfileReq: UpdateProfileReq,
        onSuccess: OnSuccess<UpdateProfileResponse>,
        onError: OnError<String>
    )

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        onSuccess: OnSuccess<ChangePasswordResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun getInstance(api: MyProfileApi): IMyProfileRepository = MyProfileRepository(api)
    }

}