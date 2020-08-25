package com.bpositive.technician.myWorks.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpositive.technician.BaseViewModel
import com.bpositive.technician.settings.model.LanguageModel
import com.bpositive.technician.settings.model.SettingMenus
import com.bpositive.technician.myWorks.service.repository.SettingRepository

class MyWorksViewModel : BaseViewModel() {

    val toastMessage = MutableLiveData<String>()

    private var _dataLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> = _dataLoading

    private var _languageList =
        MutableLiveData<MutableList<LanguageModel>>().apply { value = mutableListOf() }

    val languageList: MutableLiveData<MutableList<LanguageModel>>
        get() = _languageList


    private var _settingMenu =
        MutableLiveData<List<SettingMenus.Details>>().apply { value = mutableListOf() }

    val settingMenu: MutableLiveData<List<SettingMenus.Details>>
        get() = _settingMenu


    private val settingRepository by lazy {
        SettingRepository()
    }

    override fun start() {
//        _dataLoading.value = true
//        settingRepository.getLanguageResponse(onSuccess = {
//            _dataLoading.value = false
//            _languageList = it as MutableLiveData<MutableList<LanguageModel>>
//        }, onError = {
//            _dataLoading.value = false
//        })
        _dataLoading.value = true

        Log.i("_dataLoading", "--->>> " + _dataLoading.value)
        settingRepository.onLoadSelectedMenuItems(onSuccess = {
            //   _dataLoading.value = false
            Log.i("_dataLoading", "--->>> " + _dataLoading.value)
            _settingMenu.value = it.details
        }, onError = {
            // _dataLoading.value = false
        })
    }

    /*save selected menus*/
    fun onClick() {
        // _dataLoading.value = true

        toastMessage.value = "Menu saved successfully"
//        settingRepository.onSaveSelectedMenuItems(onSuccess = {
//          //  _dataLoading.value = false
//            _languageList = it as MutableLiveData<MutableList<LanguageModel>>
//        }, onError = {
//           // _dataLoading.value = false
//        })
    }

    override fun onCleared() {
        super.onCleared()
        settingRepository.completedJob.cancel()
    }

}