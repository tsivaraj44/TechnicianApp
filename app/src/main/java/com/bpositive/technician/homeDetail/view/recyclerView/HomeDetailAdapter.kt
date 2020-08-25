package com.bpositive.technician.homeDetail.view.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.databinding.HomeDetailItemBinding
import com.bpositive.technician.homeDetail.model.Details
import com.bpositive.technician.homeDetail.viewModel.HomeDetailViewModel
import com.bpositive.technician.utils.HomeDetailsAdapter.CONTRIBUTE
import com.bpositive.technician.utils.HomeDetailsAdapter.DESCRIPTION
import com.bpositive.technician.utils.HomeDetailsAdapter.FOLLOW

typealias homeDetailListener = (Details, Int) -> Unit

class HomeDetailAdapter(
    val viewModel: HomeDetailViewModel,
    val homeDetailListener: homeDetailListener
) : RecyclerView.Adapter<HomeDetailAdapter.DetailHolder>() {

    private val homeDetailList = mutableListOf<Details>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        return DetailHolder(HomeDetailItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = homeDetailList.size

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.bindUI(position)
    }

    fun addDetailList(mHomeDetailList: List<Details>) {
        val position = itemCount
        homeDetailList.addAll(mHomeDetailList)
        notifyItemRangeInserted(position, homeDetailList.size - 1)
    }

    fun clear() {
        homeDetailList.clear()
        notifyDataSetChanged()
    }

    inner class DetailHolder(val view: HomeDetailItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindUI(position: Int) {
            view.apply {
                homeDetail = homeDetailList[position]

                val homeDetailListener = object : HomeDetailListener {
                    override fun onContributeClicked(id:Int, homeDetail: Details?) {
                        when(id) {
                            1-> {      //  Contribute
                                homeDetailListener.invoke(homeDetail as Details, CONTRIBUTE)
                            }
                            2 -> {     //  Detail
                                homeDetailListener.invoke(homeDetail as Details, DESCRIPTION)
                            }
                            3 -> {     //  Follow
                                FOLLOW
                            }
                        }
                    }
                }
                listener = homeDetailListener
                /*ilProgress.homeDetail = homeDetailList[position]
                ilProgress.listener = homeDetailListener*/
            }
        }
    }

    interface HomeDetailListener {
        fun onContributeClicked(id: Int, homeDetail: Details? = null)
    }

}

