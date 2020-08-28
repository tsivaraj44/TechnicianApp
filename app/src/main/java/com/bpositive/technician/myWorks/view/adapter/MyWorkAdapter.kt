package com.bpositive.technician.myWorks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.R
import com.bpositive.technician.myWorks.model.response.Works
import com.bpositive.technician.utils.TravelStatus
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_admin_map_lisview_card_item.view.*

typealias works = (Works) -> Unit

class MyWorkAdapter(val type: Int, val works: works) :
    RecyclerView.Adapter<MyWorkAdapter.WorkHolder>() {

    private val worksList: ArrayList<Works> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHolder {
        return WorkHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_admin_map_lisview_card_item, parent, false)
        )
    }

    override fun getItemCount() = worksList.size

    override fun onBindViewHolder(holder: WorkHolder, position: Int) {
        holder.bindUI(position)
    }

    fun addWorkList(_worksList: List<Works>) {
        worksList.clear()
        worksList.addAll(_worksList)
        notifyDataSetChanged()
    }

    inner class WorkHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(position: Int) {
            view.apply {

                val data = worksList[position]

                txt_map_listview_title.text = data.customerName
                txt_map_listview_status.text = data.customerNumber
                txt_map_listview_status_info.text = data.reason

                Glide.with(this).load(data.categoryImage).into(img_map_navigation)

                item_root_admin_map_listview.setOnClickListener {
                    if (lay_expand_map_listview_details.visibility == View.VISIBLE) {
                        lay_expand_map_listview_details.visibility = View.GONE
                    } else {
                        if (type != TravelStatus.COMPLETED)
                            lay_expand_map_listview_details.visibility = View.VISIBLE
                    }
                }

                when (type) {
                    TravelStatus.IN_PROGRESS -> {
                        txt_map_listview_action.text = "Move To"
                    }
                    TravelStatus.PENDING -> {
                        txt_map_listview_action.text = "Complete"
                    }
                    TravelStatus.COMPLETED -> {
                        lay_expand_map_listview_details.visibility = View.GONE
                    }
                }
                lay_expand_map_listview_details.setOnClickListener {
                    works.invoke(data)
                }

            }
        }
    }
}