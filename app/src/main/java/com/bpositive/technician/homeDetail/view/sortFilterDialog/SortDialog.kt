package com.bpositive.technician.homeDetail.view.sortFilterDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.bpositive.R
import com.bpositive.technician.utils.orderByConstants.ASCENDING
import com.bpositive.technician.utils.orderByConstants.CREATED_DATE
import com.bpositive.technician.utils.orderByConstants.DESCENDING
import com.bpositive.technician.utils.orderByConstants.NATIONAL_NUMBER
import com.bpositive.technician.utils.orderByConstants.REQUIRED_AMOUNT
import kotlinx.android.synthetic.main.fragment_sort_dialog.*

typealias SortResult = (String, Int) -> Unit

class SortDialog(
    private val sortResult: SortResult
) : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvSortCaseNumberAsc.setOnClickListener(this)
        tvSortCaseNumberDesc.setOnClickListener(this)
        tvSortDateAsc.setOnClickListener(this)
        tvSortDateDesc.setOnClickListener(this)
        tvRequiredAmountAsc.setOnClickListener(this)
        tvRequiredAmountDesc.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSortCaseNumberAsc -> {
                sortResult.invoke(NATIONAL_NUMBER, ASCENDING)
            }
            R.id.tvSortCaseNumberDesc -> {
                sortResult.invoke(NATIONAL_NUMBER,DESCENDING)
            }
            R.id.tvSortDateAsc -> {
                sortResult.invoke(CREATED_DATE, ASCENDING)
            }
            R.id.tvSortDateDesc -> {
                sortResult.invoke(CREATED_DATE, DESCENDING)
            }
            R.id.tvRequiredAmountAsc -> {
                sortResult.invoke(REQUIRED_AMOUNT, ASCENDING)
            }
            R.id.tvRequiredAmountDesc -> {
                sortResult.invoke(REQUIRED_AMOUNT, DESCENDING)
            }
        }

        dialog?.dismiss()
    }

    companion object {
        private val TAG = SortDialog.javaClass.simpleName
        fun showSortDialog(
            fragmentManager: FragmentManager,
            sortResult: SortResult
        ) = SortDialog(
            sortResult
        ).show(fragmentManager,
            TAG
        )
    }

}