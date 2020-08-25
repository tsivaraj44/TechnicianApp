package com.bpositive.technician.homeDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpositive.technician.BaseViewModel
import com.bpositive.technician.homeDetail.model.Details
import com.bpositive.technician.homeDetail.model.InitiatorsItem
import com.bpositive.technician.homeDetail.model.RequestHomeDetail
import com.bpositive.technician.homeDetail.service.repository.HomeDetailRepository
import com.bpositive.technician.utils.OnSuccess

class HomeDetailViewModel : BaseViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _projectList = MutableLiveData<MutableList<Details>>().apply { value = mutableListOf() }

    val projectList: MutableLiveData<MutableList<Details>>
        get() = _projectList

    private val homeDetailRepository by lazy {
        HomeDetailRepository()
    }

    fun getHomeDetailResponse(
        requestHomeDetail: RequestHomeDetail,
        onSuccess: OnSuccess<Boolean>,
        isListAvailable: OnSuccess<Boolean>,
        onSuccessInitiatorList: OnSuccess<List<InitiatorsItem>>
    ) {
        _dataLoading.value = true
        homeDetailRepository.getHomeDetailResponse(requestHomeDetail, onSuccess = {
            _dataLoading.value = false
            onSuccess.invoke(false)
            (it.detailList!! as MutableList<Details>).apply {
                if(isNullOrEmpty())
                    isListAvailable.invoke(false)
                else
                    isListAvailable.invoke(true)
                _projectList.value = this
            }
            onSuccessInitiatorList(it.initiatorList as List<InitiatorsItem>)
        }, onError = {
            _dataLoading.value = false
            onSuccess.invoke(true)
        }
        )
    }

    override fun start() {}

    override fun onCleared() {
        super.onCleared()
        homeDetailRepository.completedJob.cancel()
    }

}
