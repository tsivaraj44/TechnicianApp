package com.bpositive.technician.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.R
import com.bpositive.technician.home.model.Settlement
import kotlinx.android.synthetic.main.item_admin_map_lisview_card_item.view.*

typealias settlement = (Settlement) -> Unit

class SettlementAdapter(val settlement: settlement) :
    RecyclerView.Adapter<SettlementAdapter.SettlementHolder>() {

    private val settlementsList: ArrayList<Settlement> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettlementHolder {
        return SettlementHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_admin_map_lisview_card_item, parent, false)
        )
    }

    override fun getItemCount() = settlementsList.size

    override fun onBindViewHolder(holder: SettlementHolder, position: Int) {
        holder.bindUI(position)
    }

    fun addSettlementList(_SettlementsList: List<Settlement>) {
        settlementsList.clear()
        settlementsList.addAll(_SettlementsList)
        notifyDataSetChanged()
    }

    inner class SettlementHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(position: Int) {
            view.apply {

                val data = settlementsList[position]

                txt_map_listview_title.text = data.customerName
                txt_map_listview_status.text = data.totalAmount
                txt_map_listview_status_info.text = data.visitDate + " " + data.visitTime

                img_map_navigation.visibility = View.GONE
                img_map_listview_photo.visibility = View.GONE
                img_map_listview_video.visibility = View.GONE
                img_device_res_notification_type.visibility = View.GONE
                itemView.setOnClickListener {
                    settlement.invoke(data)
                }

            }
        }
    }
}