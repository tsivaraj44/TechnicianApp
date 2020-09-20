package com.bpositive.technician.home.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.home.model.ResSettlement
import com.bpositive.technician.home.service.repository.IHomeRepository
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.response.MyWorkResponse
import com.bpositive.technician.myWorks.service.MyWorkApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.launch

class HomeViewModel(private val iHomeRepository: IHomeRepository) : ViewModel() {

    fun getSettlement(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ResSettlement>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iHomeRepository.getSettlement(profileRequest, onSuccess, onError)
        }
    }

    class Factory(val context: Context?) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val repository = IHomeRepository.getInstance(
                NetworkManager.serviceClass(MyWorkApi::class.java).create()
            )
            return HomeViewModel(repository) as T
        }
    }

}