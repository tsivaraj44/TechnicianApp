package com.bpositive.technician.homeDetail.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.bpositive.R
import com.bpositive.technician.homeDetail.model.InitiatorsItem
import java.util.*

class InitiatorSearchAdapter(
    context: Context, @LayoutRes private val layoutResource: Int,
    private var initiatorsList: List<InitiatorsItem>
) : ArrayAdapter<InitiatorsItem>(context, layoutResource, initiatorsList), Filterable {

    private val originalList = mutableListOf<InitiatorsItem>()

    private val listFilter = ListFilter()

    fun setList(list:List<InitiatorsItem>) {
        originalList.clear()
        originalList.addAll(list)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null)
            view = LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)

        view?.findViewById<TextView>(R.id.tvInitiator)?.text = getItem(position).initiatiorName
        return view!!
    }

    override fun getItem(position: Int): InitiatorsItem {
        return initiatorsList[position]
    }

    override fun getCount(): Int {
        return initiatorsList.size
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    inner class ListFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = listOf<InitiatorsItem>()
            if (constraint!= null) {
                filteredList = originalList.filter {
                    it.initiatiorName.toString().toLowerCase(Locale.getDefault()).contains(constraint, true)
                }
            }
            return FilterResults().apply {
                values = filteredList
                count = filteredList.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            initiatorsList =
                if (results?.values != null) results.values as List<InitiatorsItem> else emptyList()

            if (results?.count != 0) notifyDataSetChanged() else notifyDataSetInvalidated()
        }
    }

}
