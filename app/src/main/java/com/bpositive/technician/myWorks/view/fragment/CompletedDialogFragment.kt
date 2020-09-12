package com.bpositive.technician.myWorks.view.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bpositive.R
import com.bpositive.technician.myWorks.view.adapter.MyPictureAdapter
import com.bpositive.technician.utils.ImageConstants
import com.bpositive.technician.utils.savePic
import com.bpositive.technician.utils.showDialogToPick
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_completed_dialog.*

typealias costWithComment = (String, String) -> Unit

class CompletedDialogFragment(
    val name: String,
    val type: Boolean,
    val costWithComment: costWithComment
) : DialogFragment() {  //  type -> true(complete), false(pending)

    private val picList = mutableListOf<String>()

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 1)
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_completed_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnComplete.text = name
        ivTakePic.setOnClickListener {
            picOrTakeImage()
        }

        rvPic.adapter = MyPictureAdapter {
            picList.removeAt(it)
        }

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

    private fun picOrTakeImage() {
        if (checkRuntimePermission()) {
            this.showDialogToPick()
        } else {
            requestRuntimePermission()
        }
    }

    private fun checkRuntimePermission(): Boolean {
        val writeablePermission =
            ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        if (writeablePermission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestRuntimePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                val granted = grantResults.isNotEmpty()
                        && permissions.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    permissions[0]
                )
                when (granted) {
                    true -> this.showDialogToPick()
                    else -> activity?.toast(resources.getString(R.string.permission_denied))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ImageConstants.GALLERY -> {
                    try {
                        val uri = data?.data
                        val bitmap = if (Build.VERSION.SDK_INT < 28) {
                            MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                        } else {
                            val source =
                                ImageDecoder.createSource(context?.contentResolver!!, uri!!)
                            ImageDecoder.decodeBitmap(source)
                        }
                        picList.add(context?.savePic(bitmap).toString())
                        (rvPic.adapter as MyPictureAdapter).addPic(bitmap)
                    } catch (e: Exception) {
                        context?.toast("Failed to load image")
                    }
                }
                ImageConstants.CAMERA -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    picList.add(context?.savePic(bitmap).toString())
                    (rvPic.adapter as MyPictureAdapter).addPic(bitmap)
                }
            }
        }
    }

    companion object {
        private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
        fun getInstance(name: String, type: Boolean, costWithComment: costWithComment) =
            CompletedDialogFragment(name, type, costWithComment)
    }

}