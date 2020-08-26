package com.bpositive.technician.myWorks.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bpositive.R
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_completed_dialog.*

typealias costWithComment = (String, String) -> Unit

class CompletedDialogFragment(val type: Boolean, val costWithComment: costWithComment) :
    DialogFragment() {  //  type -> true(complete), false(pending)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_completed_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!type)
            btnComplete.text = "Move to pending"

        btnComplete.setOnClickListener {
            if (isFieldValid()) {
                costWithComment.invoke(etCost.text.toString(), etComment.text.toString())
                this.dismiss()
            } else
                activity?.toast("Fill all the fields")
        }

    }

    private fun isFieldValid(): Boolean {
        if (TextUtils.isEmpty(etCost.text.toString()))
            return false
        if (TextUtils.isEmpty(etComment.text.toString()))
            return false
        return true
    }

    companion object {
        fun getInstance(type: Boolean, costWithComment: costWithComment) =
            CompletedDialogFragment(type, costWithComment)
    }

}