package com.bpositive.technician.language.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.databinding.ItemLanguageSelectionBinding
import com.bpositive.technician.language.model.LanguageData
import com.bpositive.technician.language.viewModel.LanguageViewModel

class LanguageSelectionAdapter(var languageViewModel: LanguageViewModel?) :
    RecyclerView.Adapter<LanguageSelectionAdapter.LanguageViewHolder>() {

    private var langItems: List<LanguageData>? = null

    fun setLangItems(langItems: List<LanguageData>?) {

        println("LanguageSelectionAdapter: $langItems")

        if (this.langItems == null)
            this.langItems = langItems

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {

        val itemBinding = ItemLanguageSelectionBinding.inflate(LayoutInflater.from(parent.context))
        return LanguageViewHolder(itemBinding)

    }

    override fun getItemCount(): Int {
        return if (langItems == null) 0 else langItems!!.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.binding.languageModel = langItems?.get(position)

        val listener = object : LanguageSelectedListener {
            override fun onLanguageSelected(itemData: LanguageData) {
                languageViewModel?.onClickedItem(itemData)
            }
        }
        holder.binding.listener = listener
        holder.binding.executePendingBindings()
    }

    class LanguageViewHolder(val binding: ItemLanguageSelectionBinding) :
        RecyclerView.ViewHolder(binding.root)


}
