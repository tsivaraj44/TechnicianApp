package com.bpositive.technician.settings.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.databinding.AdapterSettingHomeMenuBinding
import com.bpositive.technician.settings.model.SettingMenus
import com.bpositive.technician.utils.GridViewItems.GRID_ITEMS
import kotlinx.android.synthetic.main.adapter_setting_home_menu.view.*

class SettingMenusAdapter(
    var items: ArrayList<SettingMenus.Details>,
    var listener: HomePageOnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface HomePageOnItemClickListener {
        fun menuItemClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)

        val adapterSettingHomeMenuBinding =
            AdapterSettingHomeMenuBinding.inflate(layoutInflater, parent, false)
        return adapterSettingHomeMenuBinding?.let { HomePageAdapterViewHolder(it) }!!
    }

    inner class HomePageAdapterViewHolder(private var it: AdapterSettingHomeMenuBinding) :
        RecyclerView.ViewHolder(it.root) {


        fun bind(
            mainModel: SettingMenus.Details,
            clickListener: HomePageOnItemClickListener
        ) {
            // once we change grid view layout manager count means need to check this
            for (x in -1 until items.size step GRID_ITEMS) {
                local.dividerList.add(x)
            }

            it.adapterSettingsMenuItem = mainModel
            it.root.setOnClickListener {
                clickListener.menuItemClickListener(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.i("itemsSize", "--->>> " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomePageAdapterViewHolder) {
            populateItemRows(holder, position)
        }
    }

    private fun populateItemRows(holder: HomePageAdapterViewHolder, position: Int) {
        if (position >= (items.size - 3)) {
            holder.itemView.bottomDividerView.visibility = View.GONE
        } else {
            holder.itemView.bottomDividerView.visibility = View.VISIBLE
        }

        if (local.dividerList.contains(position)) {
            holder.itemView.rightSideDividerView.visibility = View.GONE
        } else {
            holder.itemView.rightSideDividerView.visibility = View.VISIBLE
        }
        var appData = items.get(position)
        (holder).bind(appData!!, listener)
    }
}

object local {
    var dividerList = arrayListOf<Int>()
}
