package com.bpositive.technician.login.service

import com.bpositive.technician.login.model.LoginRequest
import com.bpositive.technician.login.model.LoginResponse
import com.bpositive.technician.utils.OnError
import com.bpositive.technician.utils.OnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val api: LoginApi
) : ILoginRepository {

    override suspend fun doLogin(
        loginRequest: LoginRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.doLogin(loginRequest = loginRequest)
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

interface ILoginRepository {
    suspend fun doLogin(
        loginRequest: LoginRequest,
        onSuccess: OnSuccess<LoginResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun getInstance(api: LoginApi): ILoginRepository = LoginRepository(api)
    }

}