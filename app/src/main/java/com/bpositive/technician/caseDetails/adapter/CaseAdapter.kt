package com.bpositive.technician.caseDetails.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.technician.caseDetails.model.CaseDetailsModel
import com.bpositive.databinding.AdapterCaseDetailsBinding

class CaseAdapter(
    var items: MutableList<CaseDetailsModel.Source>,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun itemClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)

        val adapterCaseDetailsBinding =
            AdapterCaseDetailsBinding.inflate(layoutInflater, parent, false)
        return adapterCaseDetailsBinding?.let { CaseAdapterViewHolder(it) }!!
    }

    inner class CaseAdapterViewHolder(private var it: AdapterCaseDetailsBinding) :
        RecyclerView.ViewHolder(it.root) {
        fun bind(
            mainModel: CaseDetailsModel.Source,
            clickListener: CaseAdapter.OnItemClickListener
        ) {
            it.caseItem = mainModel
            it.root.setOnClickListener {
                clickListener.itemClickListener(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.i("itemsSize", "--->>> " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CaseAdapterViewHolder) {
            populateItemRows(holder, position)
        }
    }

    private fun populateItemRows(holder: CaseAdapterViewHolder, position: Int) {
        var appData = items.get(position)
        (holder).bind(appData!!, listener)
    }
}