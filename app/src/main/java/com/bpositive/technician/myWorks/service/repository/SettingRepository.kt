package com.bpositive.technician.myWorks.service.repository

import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.settings.model.LanguageModel
import com.bpositive.technician.settings.model.SettingMenus
import com.bpositive.technician.myWorks.service.SettingService
import com.bpositive.technician.utils.*
import kotlinx.coroutines.*

class SettingRepository {


    //http://192.168.1.104:1041/coreapiv1/type=settings_detail?type=settings_detail&lang=en
    //http://192.168.1.104:1041/coreapiv1/index?type=settings_detail&lang=en
    //http://192.168.1.104:1041/coreapiv1/index?type=settings_detail&lang=en
    val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)

    private val settingLanguageRepositoryService: SettingService by lazy {
        NetworkManager.baseURL(BaseData.BASE_URL).serviceClass(SettingService::class.java)
            .create<SettingService>()
    }

    fun getLanguageResponse(onSuccess: OnSuccess<LanguageModel>, onError: OnError<Throwable>) =
        backgroundScope.launch {
            when (val result: Result<LanguageModel> =
                settingLanguageRepositoryService.callLanguageList(APITypes.getcoreconfig)
                    .awaitResult()) {
                is Result.Ok -> withContext(Dispatchers.Main) { onSuccess(result.value) }
                is Result.Error -> withContext(Dispatchers.Main) { onError(result.exception) }
                is Result.Exception -> withContext(Dispatchers.Main) { onError(result.exception) }
            }
        }

    fun onLoadSelectedMenuItems(onSuccess: OnSuccess<SettingMenus>, onError: OnError<Throwable>) =
        backgroundScope.launch {
            //            var jsonObject = JSONObject()
//            jsonObject.put("", "")
//            var jsonData = jsonObject.toString()
//            Log.i("jsonObject", "--->>>> " + jsonData)
            var settingsRequest = com.bpositive.technician.settings.model.SettingsRequest("")

            when (val result: Result<SettingMenus> =
                settingLanguageRepositoryService.getSettingsDomainList(
                    APITypes.getSettingsDetail,
                    "",
                    settingsRequest
                )
                    .awaitResult()) {
                is Result.Ok -> withContext(Dispatchers.Main) { onSuccess(result.value) }
                is Result.Error -> withContext(Dispatchers.Main) { onError(result.exception) }
                is Result.Exception -> withContext(Dispatchers.Main) { onError(result.exception) }
            }
        }

    fun onSaveSelectedMenuItems(onSuccess: OnSuccess<LanguageModel>, onError: OnError<Throwable>) =
        backgroundScope.launch {
            when (val result: Result<LanguageModel> =
                settingLanguageRepositoryService.callLanguageList(APITypes.getcoreconfig)
                    .awaitResult()) {
                is Result.Ok -> withContext(Dispatchers.Main) { onSuccess(result.value) }
                is Result.Error -> withContext(Dispatchers.Main) { onError(result.exception) }
                is Result.Exception -> withContext(Dispatchers.Main) { onError(result.exception) }
            }
        }

}