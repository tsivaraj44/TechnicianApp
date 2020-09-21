package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bpositive.R
import kotlinx.android.synthetic.main.dialog_enter_otp.*

typealias verifyOtp = (String) -> Unit

class EnterOtpDialog(val otp: String, val verifyOtp: verifyOtp) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_enter_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        tvOtp.text = "Enter this otp below: $otp"

        btnVerifyOtp.setOnClickListener {
            if (TextUtils.isEmpty(etOTP.text.toString())) {
                etOTP.error = "Need to enter Otp"
                return@setOnClickListener
            } else if (etOTP.text.toString().length != 4) {
                etOTP.error = "Must be a 4-digit code"
                return@setOnClickListener
            }
            verifyOtp.invoke(etOTP.text.toString())
            dialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 1)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        private val TAG = EnterOtpDialog.javaClass.simpleName
        fun showEnterOtpDialog(fragmentManager: FragmentManager, otp: String, verifyOtp: verifyOtp) =
            EnterOtpDialog(otp, verifyOtp).show(fragmentManager, TAG)
    }

}