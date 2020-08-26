package com.bpositive.technician.myWorks.service.repository

import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.model.response.StartWorkResponse
import com.bpositive.technician.myWorks.service.MyWorkApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorkRepository(
    private val api: MyWorkApi
) : IMyWorkRepository {

    override suspend fun getWorkList(
        myWorkRequest: MyWorkRequest,
        onSuccess: OnSuccess<MyWorkResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.getMyWork(myWorkRequest = myWorkRequest)
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

    override suspend fun startWork(
        startWorkRequest: StartWorkRequest,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.startWork(startWorkRequest = startWorkRequest)
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

    override suspend fun moveToPending(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.moveToPending(moveToPendingReq = moveToPendingReq)
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

    override suspend fun completeWork(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.completeWork(moveToPendingReq = moveToPendingReq)
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

interface IMyWorkRepository {
    suspend fun getWorkList(
        myWorkRequest: MyWorkRequest,
        onSuccess: OnSuccess<MyWorkResponse>,
        onError: OnError<String>
    )

    suspend fun startWork(
        startWorkRequest: StartWorkRequest,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    )

    suspend fun moveToPending(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    )

    suspend fun completeWork(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun getInstance(api: MyWorkApi): IMyWorkRepository = MyWorkRepository(api)
    }

}