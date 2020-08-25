package com.bpositive.technician.homeDetail.view.recyclerView

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.technician.homeDetail.model.Details

object HomeDetailBinding {
    @JvmStatic
    @BindingAdapter("items")
    fun setItem(recyclerView: RecyclerView, homeDetailList: List<Details>) {
        (recyclerView.adapter as HomeDetailAdapter).addDetailList(homeDetailList)
    }
}