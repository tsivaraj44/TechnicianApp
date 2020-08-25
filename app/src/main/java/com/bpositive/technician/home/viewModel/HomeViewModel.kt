package com.bpositive.technician.home.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpositive.technician.BaseViewModel
import com.bpositive.technician.home.model.DomainListItems
import com.bpositive.technician.home.model.HomeDomainListResponse
import com.bpositive.technician.home.service.repository.HomeRepository
import com.bpositive.technician.utils.TaskCallback

class HomeViewModel : BaseViewModel() {

    val _clickedItemLiveData = MutableLiveData<DomainListItems>()

    val toastMessage = MutableLiveData<String>()

    val isLoading = MutableLiveData<Int>()
    val isListAvailable = MutableLiveData<Int>()

    var _responseDetails = MutableLiveData<HomeDomainListResponse>()

    var _domainListItems = MutableLiveData<List<DomainListItems>>()

    private val homeRepository: HomeRepository by lazy {
        HomeRepository()
    }

    override fun start() {
        isLoading.value = View.VISIBLE
        homeRepository.callDomainListAPI(object : TaskCallback<HomeDomainListResponse> {
            override fun onComplete(result: HomeDomainListResponse?) {
                if (result?.status == 1) {
                    _responseDetails.value = result
                    _domainListItems.value = result.domainItems
                    if (result.domainItems.isNullOrEmpty())
                        isListAvailable.value = View.VISIBLE
                    else
                        isListAvailable.value = View.GONE
                }
                toastMessage.value = result?.message
                isLoading.value = View.GONE
            }

            override fun onException(t: Throwable?) {
                toastMessage.value = t?.localizedMessage
                isLoading.value = View.GONE
            }
        })
    }

    val responseDetails: LiveData<HomeDomainListResponse>
        get() = _responseDetails

    val domainListItems: LiveData<List<DomainListItems>>
        get() = _domainListItems

    val clickedItemLiveData: LiveData<DomainListItems>
        get() = _clickedItemLiveData

    fun selectedDomainItems(selectedDomain: DomainListItems) {

        println("selectedDomainItems: ${selectedDomain.domain_name}")

        _clickedItemLiveData.value = selectedDomain

    }

    override fun onCleared() {
        super.onCleared()
        homeRepository.completedJOb.cancel()
    }

}