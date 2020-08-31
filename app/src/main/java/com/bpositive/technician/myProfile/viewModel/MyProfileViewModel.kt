package com.bpositive.technician.myProfile.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.myProfile.model.*
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.myProfile.service.IMyProfileRepository
import com.bpositive.technician.myProfile.service.MyProfileApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.launch

class MyProfileViewModel(private val iMyProfileRepository: IMyProfileRepository) : ViewModel() {

    fun getMyProfile(
        profileRequest: ProfileRequest,
        onSuccess: OnSuccess<ProfileResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyProfileRepository.getMyProfile(profileRequest, onSuccess, onError)
        }
    }

    fun updateMyProfile(
        updateProfileRequest: UpdateProfileRequest,
        onSuccess: OnSuccess<UpdateProfileResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyProfileRepository.updateMyProfile(updateProfileRequest, onSuccess, onError)
        }
    }

    fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        onSuccess: OnSuccess<ChangePasswordResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            iMyProfileRepository.changePassword(changePasswordRequest, onSuccess, onError)
        }
    }

    class Factory(val context: Context) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val repository = IMyProfileRepository.getInstance(
                NetworkManager.serviceClass(MyProfileApi::class.java).create()
            )
            return MyProfileViewModel(repository) as T
        }
    }

}
