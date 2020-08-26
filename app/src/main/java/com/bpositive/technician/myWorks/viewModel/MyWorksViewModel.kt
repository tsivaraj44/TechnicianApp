package com.bpositive.technician.myWorks.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.model.response.StartWorkResponse
import com.bpositive.technician.myWorks.service.MyWorkApi
import com.bpositive.technician.myWorks.service.repository.IMyWorkRepository
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.launch

class MyWorksViewModel(private val iMyWorkRepository: IMyWorkRepository) : ViewModel() {

    fun getWorkList(
        myWorkRequest: MyWorkRequest,
        onSuccess: OnSuccess<MyWorkResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyWorkRepository.getWorkList(myWorkRequest, onSuccess, onError)
        }
    }

    fun startWork(
        startWorkRequest: StartWorkRequest,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyWorkRepository.startWork(startWorkRequest, onSuccess, onError)
        }
    }

    fun moveToPending(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyWorkRepository.moveToPending(moveToPendingReq, onSuccess, onError)
        }
    }

    fun completeWork(
        moveToPendingReq: MoveToPendingReq,
        onSuccess: OnSuccess<StartWorkResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyWorkRepository.completeWork(moveToPendingReq, onSuccess, onError)
        }
    }

    class Factory(val context: Context?) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val repository = IMyWorkRepository.getInstance(
                NetworkManager.serviceClass(MyWorkApi::class.java).create()
            )
            return MyWorksViewModel(repository) as T
        }
    }

}