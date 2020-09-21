package com.bpositive.technician.myWorks.service.repository

import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.model.response.StartWorkResponse
import com.bpositive.technician.myWorks.service.MyWorkApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
        files: MutableList<String>,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
//            try {
            val technicianId = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                Gson().toJson(moveToPendingReq.technicianId)
            )
            val jobId = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                Gson().toJson(moveToPendingReq.jobId)
            )
            val amount = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                Gson().toJson(moveToPendingReq.amount)
            )
            val comments = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                Gson().toJson(moveToPendingReq.comments)
            )

            if (files.size < 3) {
                for (i in 0..3) {
                    files.add("")
                }
            }

            val file1 = File(files[0])
            val file2 = File(files[1])
            val file3 = File(files[2])

            val jobAttachment1 = RequestBody.create(MediaType.parse("image/*"), file1)
            val jobAttachment2 = RequestBody.create(MediaType.parse("image/*"), file2)
            val jobAttachment3 = RequestBody.create(MediaType.parse("image/*"), file3)

            val fileJobAttachment1 =
                MultipartBody.Part.createFormData("job_attachments_1", file1.name, jobAttachment1)
            val fileJobAttachment2 =
                MultipartBody.Part.createFormData("job_attachments_2", file2.name, jobAttachment2)
            val fileJobAttachment3 =
                MultipartBody.Part.createFormData("job_attachments_3", file3.name, jobAttachment3)

            val response = api.completeWork(
                technicianId = technicianId,
                jobId = jobId,
                amount = amount,
                comments = comments,
                fileJobAttachment1 = fileJobAttachment1,
                fileJobAttachment2 = fileJobAttachment2,
                fileJobAttachment3 = fileJobAttachment3
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.status!!)
                        withContext(Dispatchers.Main) { onSuccess(it) }
                    else
                        withContext(Dispatchers.Main) { onError(it.message.toString()) }
                }
            } else
                withContext(Dispatchers.Main) { onError(response.message().toString()) }
            /*} catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { onError(e.toString()) }
            }*/
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
        files: MutableList<String>,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun getInstance(api: MyWorkApi): IMyWorkRepository = MyWorkRepository(api)
    }

}