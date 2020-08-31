package com.bpositive.technician.myProfile.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bpositive.R
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.dialog_change_password.*

typealias changePwd = (ChangePasswordRequest) -> Unit

class ChangePasswordDialog(private val changePwd: changePwd) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        btnCancel.setOnClickListener {
            dialog?.cancel()
        }
        btnChangePwd.setOnClickListener {
            if (isFieldValid()) {
                changePwd.invoke(
                    ChangePasswordRequest(
                        newPassword = etNewPwd.text.toString(),
                        technicianId = PreferenceManager(context!!).getTechnicianId()
                    )
                )
                dialog?.dismiss()
            } else {
                val errorMessage = when {
                    TextUtils.isEmpty(etOldPwd.text.toString()) -> resources.getString(R.string.enter_old_password)
                    TextUtils.isEmpty(etNewPwd.text.toString()) -> resources.getString(R.string.enter_new_password)
                    TextUtils.isEmpty(etConfirmPwd.text.toString()) -> resources.getString(R.string.enter_confirm_password)
                    etNewPwd.text.toString() != etConfirmPwd.text.toString() -> resources.getString(
                        R.string.incorrect_password
                    )
                    else -> ""
                }
                if (errorMessage.isNotEmpty()) {
                    activity?.toast(errorMessage)
                }
            }
        }
    }

    private fun isFieldValid(): Boolean {
        return when {
            TextUtils.isEmpty(etOldPwd.text.toString()) -> false
            TextUtils.isEmpty(etNewPwd.text.toString()) -> false
            TextUtils.isEmpty(etConfirmPwd.text.toString()) -> false
            etNewPwd.text.toString() != etConfirmPwd.text.toString() -> false
            else -> true
        }
    }

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 1)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        private val TAG = ChangePasswordDialog.javaClass.simpleName
        fun showChangePwdDialog(fragmentManager: FragmentManager, changePwd: changePwd) =
            ChangePasswordDialog(changePwd).show(fragmentManager, TAG)
    }

}