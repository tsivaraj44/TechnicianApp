package com.bpositive.technician.homeDetail.view.sortFilterDialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.bpositive.R
import com.bpositive.technician.utils.convertDateToLong
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_filter_dialog.*
import java.util.*

typealias FilterResult = (String, String) -> Unit

class FilterDialog(
    private val filterResult: FilterResult
) : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvStartDate.tag = ""
        tvEndDate.tag = ""
        tvStartDate.setOnClickListener(this)
        tvEndDate.setOnClickListener(this)
        btnFilter.setOnClickListener(this)
    }

    private fun TextView.getDate(isStartDate: Boolean, tvStartDate: TextView, tvEndDate: TextView) {
        val cal = Calendar.getInstance()
        val CurrentYear = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                this.text = "$year-${monthOfYear + 1}-$dayOfMonth"
                this.tag = "$year-${monthOfYear + 1}-$dayOfMonth"
            },
            CurrentYear,
            month,
            day
        )
        picker.apply {
            if (isStartDate) {
                if (TextUtils.isEmpty(tvEndDate.tag.toString())) {
                    datePicker.maxDate = System.currentTimeMillis()
                } else {
                    datePicker.maxDate = tvEndDate.tag.toString().convertDateToLong()
                }
            } else {
                datePicker.maxDate = System.currentTimeMillis()
                if (TextUtils.isEmpty(tvStartDate.tag.toString())) {
                } else {
                    datePicker.minDate = tvStartDate.tag.toString().convertDateToLong()
                }
            }
            show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvStartDate -> {
                tvStartDate.getDate(true, tvStartDate, tvEndDate)
            }
            R.id.tvEndDate -> {
                tvEndDate.getDate(false, tvStartDate, tvEndDate)
            }
            R.id.btnFilter -> {
                if (!TextUtils.isEmpty(tvStartDate.tag.toString()) && !TextUtils.isEmpty(tvEndDate.tag.toString())) {
                    filterResult.invoke(tvStartDate.text.toString(), tvEndDate.text.toString())
                    dialog?.dismiss()
                } else {
                    activity?.toast("Both dates are required")
                }
            }
        }
    }

    companion object {
        private val TAG = FilterDialog.javaClass.simpleName
        fun showFilterDialog(
            fragmentManager: FragmentManager,
            filterResult: FilterResult
        ) = FilterDialog(filterResult).show(fragmentManager, TAG)
    }

}