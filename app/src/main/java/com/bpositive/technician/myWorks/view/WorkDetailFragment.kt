package com.bpositive.technician.myWorks.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.Works
import com.bpositive.technician.myWorks.view.adapter.MyPictureAdapter
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.*
import com.bpositive.technician.utils.BundleConstants.WORK
import com.bpositive.technician.utils.BundleConstants.WORK_STATUS
import kotlinx.android.synthetic.main.fragment_work_detail.*

class WorkDetailFragment : BaseFragment() {

    override fun getTitle(): String {
        when (arguments?.get(WORK_STATUS)) {
            WorkStatus.UPCOMING -> {
                return "Work Detail"
            }
            TravelStatus.IN_PROGRESS -> {
                return "Work In-Progress"
            }
            TravelStatus.PENDING -> {
                return "Pending Work"
            }
        }
        return ""
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    val viewModel: MyWorksViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, MyWorksViewModel.Factory(context))
                .get(MyWorksViewModel::class.java)
        }
    }
    private val picList = mutableListOf<String>()
    var workStatus: Any? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.get(WORK_STATUS) == TravelStatus.IN_PROGRESS || arguments?.get(WORK_STATUS) == TravelStatus.PENDING) {
            groupComplete.visibility = View.VISIBLE
            btnStartOrComplete.text = resources.getString(R.string.action_complete)
            pbStartOrComplete.visibility = View.GONE
        }

        val work = arguments?.get(WORK) as Works
        workStatus = arguments?.get(WORK_STATUS)

        ivCategoryImage.makeCircularImage(work.categoryImage.toString(), R.drawable.ic_my_profile)
        tvCategory.text = work.categoryName
        tvCustomerName.text = work.customerName
        tvCustomerNo.text = work.customerNumber
        tvBrand.text = work.brandName
        tvModel.text = work.modelName
        tvVisitTime.text = work.visitTime

        ivTakePic.setOnClickListener {
            if (picList.size < 3)
                picOrVideo(true)
        }

        rvPic.adapter = MyPictureAdapter {
            picList.removeAt(it)
        }

        ivTakeVideo.setOnClickListener {
            picOrVideo(false)
        }

        btnStartOrComplete.setOnClickListener {
            when (workStatus) {
                WorkStatus.UPCOMING -> {
                    EnterOtpDialog.showEnterOtpDialog(
                        childFragmentManager,
                        work.otp.toString()
                    ) { otp ->
                        startWork(work, otp)
                    }
                }
                TravelStatus.IN_PROGRESS -> {
                    if (isFieldValid())
                        moveTo(work)
                }
                TravelStatus.PENDING -> {
                    if (isFieldValid())
                        moveTo(work)
                }
            }
        }

    }

    private fun startWork(work: Works, otp: String) {
        pbStartOrComplete.visibility = View.VISIBLE
        println("GET______" + work.technicianId)
        viewModel.startWork(
            startWorkRequest = StartWorkRequest(
                work.jobId.toString().toInt(),
                PreferenceManager(context!!).getTechnicianId(),
                //  work.technicianId.toString().toInt(),
                otp
            ), onSuccess = {
                workStatus = TravelStatus.IN_PROGRESS
                groupComplete.visibility = View.VISIBLE
                btnStartOrComplete.text = resources.getString(R.string.action_complete)
                pbStartOrComplete.visibility = View.GONE
                activity?.toast(it.message.toString())
            }, onError = {
                activity?.toast(it)
                pbStartOrComplete.visibility = View.GONE
            })
    }

    private fun moveTo(work: Works) {
        context?.showMoveTo {
            when (it) {
                TravelStatus.PENDING ->
                    moveToPending(work)
                TravelStatus.COMPLETED ->
                    completeWork(work)
            }
        }
    }

    private fun isFieldValid(): Boolean {
        return when {
            TextUtils.isEmpty(etAmount.text.toString()) -> {
                etAmount.error = "Amount required"
                false
            }
            TextUtils.isEmpty(etDescription.text.toString()) -> {
                etDescription.error = "Description required"
                false
            }
            else -> true
        }
    }

    private fun moveToPending(work: Works) {
        pbStartOrComplete.visibility = View.VISIBLE
        /*{"technician_id":1,"job_id":7,"amount":100.00,"comments":"some work balance"}*/
        viewModel.moveToPending(
            moveToPendingReq = MoveToPendingReq(
                jobId = work.jobId.toString().toInt(),
                technicianId = PreferenceManager(context!!).getTechnicianId(),
                amount = etAmount.text.toString().toDouble(),
                comments = etDescription.text.toString()
            ), onSuccess = {
                pbStartOrComplete.visibility = View.GONE
                activity?.toast(it.message.toString())
                activity?.onBackPressed()
            }, onError = {
                activity?.toast(it)
                pbStartOrComplete.visibility = View.GONE
            })
    }

    private fun completeWork(work: Works) {
        pbStartOrComplete.visibility = View.VISIBLE
        /*{"technician_id":1,"job_id":7,"amount":100.00,"comments":"rep changed"}*/
        viewModel.completeWork(
            moveToPendingReq = MoveToPendingReq(
                jobId = work.jobId.toString().toInt(),
                technicianId = PreferenceManager(context!!).getTechnicianId(),
                amount = etAmount.text.toString().toDouble(),
                comments = etDescription.text.toString()
            ), files = picList, onSuccess = {
                pbStartOrComplete.visibility = View.GONE
                activity?.toast(it.message.toString())
                activity?.onBackPressed()
            }, onError = {
                activity?.toast(it)
                pbStartOrComplete.visibility = View.GONE
            })
    }

    private fun picOrVideo(imageOrVideo: Boolean) {
        if (checkRuntimePermission()) {  //  true -> Image, false -> Video
            if (imageOrVideo) showDialogToPick() else showDialogToPickVideo()
        } else {
            requestRuntimePermission()
        }
    }

    private fun checkRuntimePermission(): Boolean {
        val writeablePermission1 =
            ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeablePermission2 =
            ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (writeablePermission1 != PackageManager.PERMISSION_GRANTED && writeablePermission2 != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestRuntimePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    permissions[0]
                ) && !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    permissions[1]
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
                ImageConstants.CHOOSE_VIDEO -> {
                    val selectedImageUri: Uri? = data?.data
                    val selectedPath = getPath(selectedImageUri)
                    println("GET_____VIDEO_____PATH____C______$selectedPath")
                }
                ImageConstants.TAKE_VIDEO -> {
                    val selectedImageUri: Uri? = data?.data
                    val selectedPath = getPath(selectedImageUri)
                    println("GET_____VIDEO_____PATH____T______${selectedPath}")
                }
            }
        }
    }

    private fun getPath(uri: Uri?): String? {
        var cursor = activity?.contentResolver?.query(uri!!, null, null, null, null)
        cursor?.moveToFirst()
        var documentId = cursor?.getString(0)
        documentId = documentId?.substring(documentId.lastIndexOf(":") + 1)
        cursor?.close()
        cursor = activity?.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(documentId),
            null
        )
        cursor?.moveToFirst()
        var path: String? = ""
        try {
            path = cursor?.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
            cursor?.close()
        } catch (e: Exception) {

        }
        return path
    }

    companion object {
        private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }

}
