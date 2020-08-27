package com.bpositive.technician.login.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.login.model.LoginRequest
import com.bpositive.technician.login.model.LoginResponse
import com.bpositive.technician.login.service.ILoginRepository
import com.bpositive.technician.login.service.LoginApi
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.launch

class LoginViewModel(val repository: ILoginRepository) : ViewModel() {

    fun doLogin(
        loginRequest: LoginRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.doLogin(loginRequest, onSuccess, onError)
        }
    }

    class Factory(val context: Context?) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val repository = ILoginRepository.getInstance(
                NetworkManager.serviceClass(LoginApi::class.java).create()
            )
            return LoginViewModel(repository) as T
        }
    }

}