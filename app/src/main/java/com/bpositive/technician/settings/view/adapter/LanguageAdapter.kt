package com.bpositive.technician.settings.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpositive.R
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.settings.model.LanguageModel
import com.bpositive.technician.utils.sessionNames
import kotlinx.android.synthetic.main.adapter_language.view.*

class LanguageAdapter(
    var context: Context,
    var items: ArrayList<LanguageModel>,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sessionManager = SessionManager(context)

    interface OnItemClickListener {
        fun languageItemClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LanguageAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_language, parent, false)
        )
    }

    inner class LanguageAdapterViewHolder(private var it: View) :
        RecyclerView.ViewHolder(it) {
        fun bind(
            mainModel: LanguageModel,
            clickListener: LanguageAdapter.OnItemClickListener
        ) {
            it.setOnClickListener {
                clickListener.languageItemClickListener(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LanguageAdapterViewHolder) {
            populateItemRows(holder, position)
        }
    }

    private fun populateItemRows(holder: LanguageAdapterViewHolder, position: Int) {
        val languageName = items.get(position).name.toLowerCase().take(2)
        val appData = items.get(position)
        if (sessionManager.getSession(sessionNames.USER_LANGUAGE)!!.equals(languageName)) {
            println("languageName----->>>> " + sessionManager.getSession(sessionNames.USER_LANGUAGE))
            holder.itemView.languageSelectedImage.visibility = View.VISIBLE
        } else {
            holder.itemView.languageSelectedImage.visibility = View.GONE
        }
        (holder).bind(appData, listener)
    }
}