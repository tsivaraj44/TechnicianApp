package com.bpositive.technician.splash.service.repository

import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.splash.model.GetCoreResponse
import com.bpositive.technician.splash.service.SplashService
import com.bpositive.technician.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashRepository {

    val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)
    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    private val splashService: SplashService by lazy {
        NetworkManager.baseURL(BaseData.BASE_URL).serviceClass(SplashService::class.java)
            .create<SplashService>()
    }

    /*private val splashService: SplashService by lazy {
        SplashService.create()
    }*/

    /*fun searchUser(location: String, language: String): Observable<GetcoreResponse> {

//        return splashAPIService.search(query = "location:$location+language:$language")
        return splashAPIService.search(query = location)

    }*/

    /*fun searchUser(location: String) {

        splashAPIService.searchTest(query = location).enqueue(callback)
    }*/

    /*fun searchUser(location: String): LiveData<GetcoreResponse> {

        val data = MutableLiveData<GetcoreResponse>()

        splashAPIService.searchTest(query = location).enqueue(object : Callback<GetcoreResponse> {
            override fun onFailure(call: Call<GetcoreResponse>, t: Throwable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<GetcoreResponse>,
                response: Response<GetcoreResponse>
            ) {
                data.value = response.body()
            }
        })

        return data

    }*/


    /*fun getCoreConfig(location: String, taskCallback: TaskCallback<GetcoreResponse>) {

        splashService.callGetCoreAPI(location).enqueue(object : Callback<GetcoreResponse> {
            override fun onFailure(call: Call<GetcoreResponse>, t: Throwable) {
                taskCallback.onException(t)
            }

            override fun onResponse(
                call: Call<GetcoreResponse>,
                response: Response<GetcoreResponse>
            ) {
                taskCallback.onComplete(response.body())
            }

        })

    }*/

    /*fun fetchAPI(taskCallback: TaskCallback<GetcoreResponse>) {
        GlobalScope.launch {
            val request = splashService.callGetCoreAPICoroutines(query = "Java")

            try {
                val response = request.await()

                Log.i(
                    "Splash Coroutines",
                    "Splash Coroutines response is: " + response.isSuccessful
                )

                if (response.isSuccessful)
                    taskCallback.onComplete(response.body())

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }*/

    fun getCoreAPIResponse(taskCallback: TaskCallback<GetCoreResponse>) {
        backgroundScope.launch {
            when (val result: Result<GetCoreResponse> =
                splashService.callGetCore(type = APITypes.getcoreconfig).awaitResult()) {

                is Result.Ok -> foregroundScope.launch { taskCallback.onComplete(result.value) }

                is Result.Error -> foregroundScope.launch { taskCallback.onException(result.exception) }

                is Result.Exception -> foregroundScope.launch { taskCallback.onException(result.exception) }

            }
        }
    }

}