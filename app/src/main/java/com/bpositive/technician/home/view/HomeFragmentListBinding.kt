package com.bpositive.technician.home.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.technician.home.model.DomainListItems
import com.bpositive.technician.home.view.adapter.HomeDomainListAdapter

object HomeFragmentListBinding {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<DomainListItems>?) {

        with(recyclerView.adapter as HomeDomainListAdapter) {

            if (items != null)
                setItems(items)
            
            notifyDataSetChanged()
        }

    }

}