package com.bpositive.technician.myProfile.view

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bpositive.R
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myProfile.model.UpdateProfileRequest
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.myProfile.viewModel.MyProfileViewModel
import com.bpositive.technician.utils.hideKeyboard
import com.bpositive.technician.utils.toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MyProfileFragment : Fragment() {

    private val vmProfile by lazy {
        this.let {
            ViewModelProviders.of(it, MyProfileViewModel.Factory(it.context!!))
                .get(MyProfileViewModel::class.java)
        }
    }

    private var countryCode = ""
    private var countryPosition = 0
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    private var mBitmap: Bitmap? = null
    val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)
    private val destinationFileName = "ProfileImage"
    private var profileImage: String = ""
    private var emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_my_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEditProfile.setOnClickListener {
            gpCommonBtn.visibility = View.GONE
            gpEditBtn.visibility = View.VISIBLE
            view.hideKeyboard()
            //  onImagePicker()
        }

        btnCancel.setOnClickListener {
            gpCommonBtn.visibility = View.VISIBLE
            gpEditBtn.visibility = View.GONE
        }

        btnChangePassword.setOnClickListener {
            changePassword()
        }

        btnSaveProfile.setOnClickListener {
            view.hideKeyboard()
            if (isFieldValid() && isEmailValid()) {
                updateMyProfile()
            }
        }

        getMyProfile()

    }

    private fun getMyProfile() {
        pbMyProfile?.visibility = View.VISIBLE
        vmProfile.getMyProfile(
            profileRequest = ProfileRequest(PreferenceManager(context!!).getTechnicianId()),
//            profileRequest = ProfileRequest(1),
            onSuccess = {
                it.details?.let { details ->
                    Glide.with(this)
                        .load(details.profileImage)
                        .circleCrop()
                        .into(ivProfileImage)
                    etUserName.setText(details.firstName + " " + details.lastName)
                    etEmail.setText(details.email)
                    etMobileNo.setText(details.countryCode + " " + details.mobileNumber)
                    etAddress.setText(details.address)
                    etPincode.setText(details.pincode)
                }
                pbMyProfile?.visibility = View.GONE
            },
            onError = {
                activity?.toast(it)
                pbMyProfile?.visibility = View.GONE
            })
    }

    private fun updateMyProfile() {
        pbMyProfile?.visibility = View.VISIBLE
        vmProfile.updateMyProfile(
            updateProfileRequest = UpdateProfileRequest(),
            onSuccess = {

                pbMyProfile?.visibility = View.GONE
            },
            onError = {
                activity?.toast(it)
                pbMyProfile?.visibility = View.GONE
            })
    }

    private fun changePassword() {
        ChangePasswordDialog.showChangePwdDialog(childFragmentManager) {
            pbMyProfile?.visibility = View.VISIBLE
            vmProfile.changePassword(
                changePasswordRequest = ChangePasswordRequest(),
                onSuccess = {

                    pbMyProfile?.visibility = View.GONE
                },
                onError = {
                    activity?.toast(it)
                    pbMyProfile?.visibility = View.GONE
                })
        }
    }


    private fun isFieldValid(): Boolean {
        return when {
            TextUtils.isEmpty(etUserName.text.toString()) -> {
                etUserName.error = resources.getString(R.string.username_required)
                false
            }
            TextUtils.isEmpty(etEmail.text.toString()) -> {
                etEmail.error = resources.getString(R.string.email_address_required)
                false
            }
            TextUtils.isEmpty(etMobileNo.text.toString()) -> {
                etMobileNo.error = resources.getString(R.string.mobile_no_required)
                false
            }
            TextUtils.isEmpty(etAddress.text.toString()) -> {
                etEmail.error = resources.getString(R.string.email_address_required)
                false
            }
            TextUtils.isEmpty(etPincode.text.toString()) -> {
                etMobileNo.error = resources.getString(R.string.mobile_no_required)
                false
            }
            else -> true
        }
    }

    private fun isEmailValid(): Boolean {
        return if (!etEmail.text.toString().trim().matches(emailPattern.toRegex())) {
            etEmail.error = resources.getString(R.string.valid_email_required)
            false
        } else
            true
    }

    /*private fun onImagePicker() {
        if (checkRuntimePermission()) {
            this.showDialogToPick()
        } else {
            requestRuntimePermission()
        }
    }*/

    private fun requestRuntimePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
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
                    //  true -> this.showDialogToPick()  //  openImagePicker()
                    else -> activity?.toast(resources.getString(R.string.permission_denied))
                }
            }
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UCrop.REQUEST_CROP) {
            backgroundScope.launch {
                val one = async { handleCropResult(data) }
                one.await()
                withContext(Dispatchers.Main) {
                    if (mBitmap != null)
                        activity?.findViewById<AppCompatImageView>(R.id.userImage)
                            ?.makeCircularImageWithBitmap(
                                mBitmap!!
                            )
                }
            }
        } else if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY -> {
                    val uCrop = UCrop.of(
                        Uri.fromFile(File(getRealPathFromURI(data!!.dataString))),
                        Uri.fromFile(
                            File(
                                activity!!.cacheDir,
                                destinationFileName
                            )
                        )
                    )
                        .withAspectRatio(4f, 4f)
                        .withMaxResultSize(400, 400)
                    val options = UCrop.Options()
                    options.setToolbarColor(
                        ContextCompat.getColor(
                            activity!!,
                            R.color.colorAccent
                        )
                    )
                    options.setStatusBarColor(
                        ContextCompat.getColor(
                            activity!!,
                            R.color.white
                        )
                    )
                    options.setToolbarWidgetColor(
                        ContextCompat.getColor(
                            activity!!,
                            R.color.colorPrimaryDark
                        )
                    )
                    uCrop.withOptions(options)
                    uCrop.start(
                        activity as MainActivity,
                        this@MyProfileFragment,
                        UCrop.REQUEST_CROP
                    )
                }

                CAMERA -> {
                    val mBitmap = data?.extras?.get("data") as Bitmap
                    val imagePath = context?.savePic(mBitmap)
                    UCrop.of(
                        Uri.fromFile(File(imagePath)),
                        Uri.fromFile(
                            File(
                                activity!!.cacheDir,
                                destinationFileName
                            )
                        )
                    )
                        .withAspectRatio(4f, 4f)
                        .withMaxResultSize(400, 400)
                        .start(activity as MainActivity, this@MyProfileFragment, UCrop.REQUEST_CROP)
                }
            }
        }
    }*/

    /*private fun handleCropResult(data: Intent?) {
        val resultUri = data?.let { UCrop.getOutput(it) }
        if (resultUri != null) {
            val result = getRealPathFromURI(resultUri.toString())
            val file = File(result)
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            mBitmap = BitmapFactory.decodeFile(file.absolutePath)
            val stream = ByteArrayOutputStream()
            mBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val image = stream.toByteArray()
            profileImage = encodeToString(image, DEFAULT)
        }
    }*/

    private fun getRealPathFromURI(contentURI: String?): String? {
        val contentUri = Uri.parse(contentURI)
        val cursor: Cursor? =
            activity!!.contentResolver.query(contentUri, null, null, null, null)
        return if (cursor == null) contentUri.path else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

}
