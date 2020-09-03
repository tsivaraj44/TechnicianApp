package com.bpositive.technician.myWorks.view.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_picture_layout.view.*

typealias pictures = (Int) -> Unit

class MyPictureAdapter(val pictures: pictures) :
    RecyclerView.Adapter<MyPictureAdapter.PictureHolder>() {

    private val picList: ArrayList<Bitmap> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureHolder {
        return PictureHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_picture_layout, parent, false)
        )
    }

    override fun getItemCount() = picList.size

    override fun onBindViewHolder(holder: PictureHolder, position: Int) {
        holder.bindUI(position)
    }

    fun addPic(_pic: Bitmap) {
        picList.add(_pic)
        notifyDataSetChanged()
    }

    inner class PictureHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(position: Int) {
            view.apply {
                Glide.with(this).load(picList[position]).into(ivPicture)

                ivRemove.setOnClickListener {
                    picList.removeAt(position)
                    notifyDataSetChanged()
                    pictures.invoke(position)
                }
            }
        }
    }
}