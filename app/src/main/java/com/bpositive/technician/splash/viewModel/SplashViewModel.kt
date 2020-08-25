package com.bpositive.technician.splash.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpositive.technician.BaseViewModel
import com.bpositive.technician.splash.model.GetCoreResponse
import com.bpositive.technician.splash.service.repository.SplashRepository
import com.bpositive.technician.utils.TaskCallback

class SplashViewModel : BaseViewModel() {

    val toastMessage = MutableLiveData<String>()

    private val _dataLoading = MutableLiveData<Boolean>()
    /*val dataLoading: LiveData<Boolean>
        get() = _dataLoading*/

    val data: LiveData<Boolean>
        get() = _dataLoading

//    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    /*val dataLoading: LiveData<Boolean>
        get() {
            return _dataLoading
        }*/

//    var liveData: LiveData<GetcoreResponse>? = null

    val responseMutableLiveData = MutableLiveData<GetCoreResponse>()



    private val splashRepository by lazy {
        SplashRepository()
    }

    /**
     * Start to call the Getcore API
     */
    override fun start() {
        Log.i("SplashViewModel", "Testing1 Started SplashViewModel")

//        _dataLoading.value = false

        /*val splashService = SplashRepository(SplashService.create())
//        splashService.searchUser("Java")

        liveData = splashService.searchUser("Java")*/

        /*splashRepository.getCoreConfig("Java", object : TaskCallback<GetcoreResponse> {
            override fun onComplete(result: GetcoreResponse?) {

                Log.d("SplashViewModel", "getcore response: " + result?.total_count)

                _dataLoading.value = false

                responseMutableLiveData.value = result

                toastMessage.value = "Success"

            }

            override fun onException(t: Throwable?) {
                Log.d("SplashViewModel", "getcore response: " + t?.localizedMessage)

                toastMessage.value = t?.localizedMessage
            }

        })*/


        /*splashRepository.fetchAPI(object : TaskCallback<GetcoreResponse> {
            override fun onComplete(result: GetcoreResponse?) {
                Log.i("Splash Coroutines", "Splash Coroutines count: " + result?.total_count)
            }

            override fun onException(t: Throwable?) {

            }

        })*/

        splashRepository.getCoreAPIResponse(object : TaskCallback<GetCoreResponse> {
            override fun onComplete(result: GetCoreResponse?) {
                Log.i(
                    "SplashViewModel",
                    "SplashViewModel response:" + result?.details?.contact_name
                )
//                toastMessage.value = "Success!"
                responseMutableLiveData.value = result

//                _dataLoading.value = false

            }

            override fun onException(t: Throwable?) {
                toastMessage.value = t?.localizedMessage
            }
        })

    }

    override fun onCleared() {
        super.onCleared()
        splashRepository.completedJob.cancel()
    }

}