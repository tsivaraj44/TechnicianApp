package com.bpositive.technician.language.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.technician.language.model.LanguageData
import com.bpositive.technician.language.view.adapter.LanguageSelectionAdapter

object LangFragmentBinding {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<LanguageData>?) {
        with(recyclerView.adapter as LanguageSelectionAdapter) {
            setLangItems(items)
            notifyDataSetChanged()
        }
    }


}