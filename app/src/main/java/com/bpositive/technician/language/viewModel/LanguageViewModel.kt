package com.bpositive.technician.language.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpositive.technician.BaseViewModel
import com.bpositive.technician.language.model.LanguageData

class LanguageViewModel : BaseViewModel() {


    var _langList = MutableLiveData<List<LanguageData>>()

    var _onSelectedLang = MutableLiveData<LanguageData>()

    override fun start() {

        val data = LanguageData("English", "EN")
        val data1 = LanguageData("العربية", "AR", false)

        val list = listOf(data, data1)
        _langList.value = list
    }

    val langList: LiveData<List<LanguageData>>
        get() = _langList

    val onSelectedLang: LiveData<LanguageData>
        get() = _onSelectedLang

    fun onClickedItem(item: LanguageData) {
        _onSelectedLang.value = item
    }
}