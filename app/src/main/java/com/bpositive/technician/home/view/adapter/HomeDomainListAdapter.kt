package com.bpositive.technician.home.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.databinding.ItemHomeDomainsBinding
import com.bpositive.technician.home.model.DomainListItems
import com.bpositive.technician.home.viewModel.HomeViewModel

class HomeDomainListAdapter(
    var homeViewModel: HomeViewModel?
) : RecyclerView.Adapter<HomeDomainListAdapter.DomainViewHolder>() {

    companion object {
        private val TAG = this.javaClass.simpleName
    }

    internal var items: List<DomainListItems>? = null

    fun setItems(items: List<DomainListItems>?) {

        Log.i(TAG, "setItems: $items")

        if (this.items == null)
            this.items = items

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainViewHolder {

        val itemsBinding = ItemHomeDomainsBinding.inflate(LayoutInflater.from(parent.context))

        return DomainViewHolder(itemsBinding)

    }


    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun onBindViewHolder(holder: DomainViewHolder, position: Int) {
        holder.binding.homeDomainItems = items?.get(position)

        val userItemClickListener = object : HomeDomainItemListener {
            override fun onDomainClicked(domainListItems: DomainListItems) {

                if (items != null) {
                    homeViewModel?.selectedDomainItems(items!![position])
                }
            }

        }

        holder.binding.itemClickedListener = userItemClickListener

        holder.binding.executePendingBindings()

    }


    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDomainName.text = items[position].domain_name
    }*/

    class DomainViewHolder(val binding: ItemHomeDomainsBinding) :
        RecyclerView.ViewHolder(binding.root)
}

/*
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtDomainName: TaawonTextView = view.domainNameTextView
}*/
