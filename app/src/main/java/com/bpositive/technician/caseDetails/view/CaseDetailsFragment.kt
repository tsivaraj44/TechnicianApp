package com.bpositive.technician.caseDetails.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bpositive.technician.BaseFragment
import com.bpositive.R
import com.bpositive.technician.caseDetails.adapter.CaseAdapter
import com.bpositive.technician.caseDetails.model.CaseDetailsModel
import com.bpositive.technician.caseDetails.viewModel.CaseDetailsViewModel
import com.bpositive.technician.core.widget.LoginDialog
import com.bpositive.databinding.FragmentCaseDetailsBinding
import com.bpositive.technician.homeDetail.model.Details
import com.bpositive.technician.utils.BundleConstants.CASE_DETAILS
import com.bpositive.technician.utils.FilePath
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_case_details.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class CaseDetailsFragment : BaseFragment(), CaseAdapter.OnItemClickListener,
    LoginDialog.CustomDialogListener {

    val viewModel by lazy {
        ViewModelProviders.of(this@CaseDetailsFragment).get(CaseDetailsViewModel::class.java)
    }
    private lateinit var viewDataBinding: FragmentCaseDetailsBinding
    private lateinit var caseAdapter: CaseAdapter
    private var arrayList = mutableListOf<CaseDetailsModel.Source>()
    private lateinit var loginDialog: LoginDialog
    private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200
    private var selectedPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCaseDetailsBinding.inflate(inflater, container, false)
        viewDataBinding.apply {
            lifecycleOwner = this@CaseDetailsFragment
            executePendingBindings()
        }
        return viewDataBinding.root
    }

    override fun getTitle(): String {

        return "Case Description"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        //viewModel?.start()
        startObserveResponse()
        startObserveMessage()

        arrayList = ArrayList()
        attachmentList.apply {
            layoutManager = LinearLayoutManager(context)
            LinearLayoutManager.VERTICAL
            isNestedScrollingEnabled = false
        }


        setUpValues()

        viewDataBinding.contributeTv.setOnClickListener({
            showDialog()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE -> {
                val granted = grantResults.isNotEmpty()
                        && permissions.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    permissions[0]
                )
                when (granted) {
                    true -> localDownloadToSave(selectedPosition)
                    else -> Toast.makeText(context!!, "Permission Denied", Toast.LENGTH_SHORT).show()//requestRuntimePermission()
                }
            }
        }
    }

    private fun showDialog() {
        loginDialog = LoginDialog(this)
        loginDialog.show(childFragmentManager, loginDialog.tag)
    }

    private fun setUpValues() {
        val homeDetails = arguments?.get(CASE_DETAILS) as Details

        /*Glide
            .with(this)
            .load(homeDetails.initiatorImage.toString())
            .apply(RequestOptions.circleCropTransform())
            .into(activity?.findViewById(R.id.headerImages)!!)*/

        /*Glide
            .with(this)
            .load(homeDetails.initiatorImage.toString())
            .apply(RequestOptions.circleCropTransform())
            .into(activity?.findViewById(R.id.userImage)!!)
        activity?.findViewById<AppCompatImageView>(R.id.followersIcon)?.visibility = View.VISIBLE

        activity?.nameTv?.text = homeDetails.initiatiorName
        activity?.followersTv?.text =
            homeDetails.followCount + " " + resources.getString(R.string.followers)*/
        val maximumAmount: Int =
            homeDetails.requiredAmount!!.replace(",", "")
                .toInt()
        serialNumberTv.text = resources.getString(R.string.serialNo) + " " + homeDetails.nationalNumber
        val requiredAmountCurrency: String =
            homeDetails.requiredAmount + " " + homeDetails.currencyCode.toString()
        requiredAmount.text = requiredAmountCurrency
        requiredProgressBar.apply {
            max = maximumAmount
            progress = maximumAmount
        }
        val raisedAmountCurrency: String =
            homeDetails.raisedAmount.toString() + " " + homeDetails.currencyCode.toString()
        raisedAmount.text = raisedAmountCurrency
        raisedProgressBar.apply {
            max = maximumAmount
            progress = homeDetails.raisedAmount!!.replace(",", "").toInt()
        }
        val balanceAmountCurrency: String =
            homeDetails.balanceAmount + " " + homeDetails.currencyCode.toString()
        balanceAmount.text = balanceAmountCurrency
        balanceProgressBar.apply {
            max = maximumAmount
            progress = homeDetails.balanceAmount!!.replace(",", "").toInt()
        }
        descriptionText.text = homeDetails.caseDescription
        serialNumber1.text = resources.getString(R.string.serialNo) + " " + homeDetails.nationalNumber
        dateTv.text = resources.getString(R.string.date_) + " " + homeDetails.date
        timeTv.text = resources.getString(R.string.time_) + " " + homeDetails.time
        viewers.text = homeDetails.views
        val sourceArray: List<String> = homeDetails.documents as List
        for (i in 0 until sourceArray.size) {
            if (!sourceArray.get(i).isEmpty()) {
                val extension: String =
                    sourceArray.get(i).substring(sourceArray.get(i).lastIndexOf("."))
                var data: CaseDetailsModel.Source = CaseDetailsModel.Source()
                val fileName: String =
                    sourceArray.get(i).substring(sourceArray.get(i).lastIndexOf('/') + 1)

                when (extension) {
                    ".jpeg" -> {
                        data.type = 1
                        data.url = sourceArray.get(i)
                        data.name = fileName
                    }
                    ".png" -> {
                        data.type = 1
                        data.url = sourceArray.get(i)
                        data.name = fileName
                    }
                    ".pdf" -> {
                        data.type = 2
                        data.url = sourceArray.get(i)
                        data.name = fileName
                    }
                    ".docx" -> {
                        data.type = 3
                        data.url = sourceArray.get(i)
                        data.name = fileName
                    }
                }
                arrayList.add(data)
            }
        }

        caseAdapter = CaseAdapter(arrayList, this)
        attachmentList.adapter = caseAdapter

        nestedScrollView.post(Runnable {
            nestedScrollView.scrollTo(0, 0)
        })
    }

    private fun startObserveMessage() {

    }

    private fun startObserveResponse() {

    }

    fun download(link: String, path: String) {
        URL(link).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
        Handler().postDelayed({
            Toast.makeText(context, "" + path, Toast.LENGTH_LONG).show()
        }, 2000)
    }

    override fun onCustomDialogListener() {
        loginDialog.dismiss()
    }

    override fun itemClickListener(position: Int) {
        selectedPosition = position
        if (checkRuntimePermission()) {
            localDownloadToSave(position)
        } else {
            requestRuntimePermission()
        }
    }

    private fun localDownloadToSave(position: Int) {

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val fileName: String =
            arrayList[position].url.substring(arrayList[position].url.lastIndexOf('/') + 1)

        var fileType = ""

        val extension: String =
            arrayList[position].url.substring(arrayList[position].url.lastIndexOf("."))
        /*choose file type*/
        when (extension) {
            ".jpeg" -> fileType = FilePath.IMAGE
            ".png" -> fileType = FilePath.IMAGE
            ".pdf" -> fileType = FilePath.PDF
            ".docx" -> fileType = FilePath.DOC
        }
        var name = arguments?.get(CASE_DETAILS) as Details

        val time = System.currentTimeMillis()
        val fileLocation = FilePath.MVVM_PATTERN + fileType + "_" + name.initiatiorName + time
        val apkStorage: File = File(
            Environment.getExternalStorageDirectory().toString() + "/"
                    + fileLocation
        )
        if (!apkStorage.exists()) {
            apkStorage.mkdirs()
        }
        val fullFilePath = apkStorage.path + "/" + fileName
        val createFile = File(fullFilePath)
        createFile.createNewFile()
        //   println("local storage : " + createFile)

        download(arrayList[position].url, createFile.path)
    }



    private fun checkRuntimePermission(): Boolean {

        val writeablePermission =
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        if (writeablePermission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestRuntimePermission() :Boolean {
        val writeablePermission =
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        if (writeablePermission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true

    }

    companion object {
        const val TAG = "CaseDetailsFragment"
        fun newInstanceTest() = CaseDetailsFragment()
    }


}