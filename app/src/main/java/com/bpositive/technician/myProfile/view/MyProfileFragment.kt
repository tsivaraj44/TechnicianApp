package com.bpositive.technician.myProfile.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
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
import androidx.navigation.fragment.findNavController
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.myProfile.model.request.ChangePasswordRequest
import com.bpositive.technician.myProfile.model.request.UpdateProfileReq
import com.bpositive.technician.myProfile.viewModel.MyProfileViewModel
import com.bpositive.technician.utils.ImageConstants.CAMERA
import com.bpositive.technician.utils.ImageConstants.GALLERY
import com.bpositive.technician.utils.hideKeyboard
import com.bpositive.technician.utils.savePic
import com.bpositive.technician.utils.showDialogToPick
import com.bpositive.technician.utils.toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_my_profile.*

class MyProfileFragment : BaseFragment() {

    private val vmProfile by lazy {
        this.let {
            ViewModelProviders.of(it, MyProfileViewModel.Factory(it.context!!))
                .get(MyProfileViewModel::class.java)
        }
    }

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    private var profileImage: String = ""
    private var emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun getTitle(): String {
        return "My Profile"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_my_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEditProfile.setOnClickListener {
            gpCommonBtn.visibility = View.GONE
            gpEditBtn.visibility = View.VISIBLE
            //  view.hideKeyboard()
            //  onImagePicker()
        }

        btnCancel.setOnClickListener {
            gpCommonBtn.visibility = View.VISIBLE
            gpEditBtn.visibility = View.GONE
        }

        btnChangePassword.setOnClickListener {
            changePassword()
        }
        ivLogout.setOnClickListener {
            PreferenceManager(requireContext()).saveTechnicianId(0)
            findNavController().navigate(R.id.loginFragment)
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
            onSuccess = {
                it.details?.let { details ->
                    Glide.with(this)
                        .load(details.profileImage)
                        .circleCrop()
                        .into(ivProfileImage)
                    etUserFirstName.setText(details.firstName)
                    etUserLastName.setText(details.lastName)
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
            updateProfileReq = UpdateProfileReq(
                etAddress.text.toString(),
                etPincode.text.toString(),
                "jnf66jdh77",
                PreferenceManager(context!!).getTechnicianId(),
                etUserLastName.text.toString(),
                Build.MANUFACTURER,
                etUserFirstName.text.toString(),
                etEmail.text.toString()
            ),
            onSuccess = {
                activity?.toast(it.message.toString())
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
                changePasswordRequest = ChangePasswordRequest(
                    it.newPassword,
                    it.technicianId
                ),
                onSuccess = {
                    activity?.toast(it.message.toString())
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
            TextUtils.isEmpty(etUserFirstName.text.toString()) -> {
                etUserFirstName.error = resources.getString(R.string.username_required)
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
                etAddress.error = resources.getString(R.string.address_required)
                false
            }
            TextUtils.isEmpty(etPincode.text.toString()) -> {
                etPincode.error = resources.getString(R.string.pincode_required)
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

    private fun onImagePicker() {
        if (checkRuntimePermission()) {
            this.showDialogToPick()
        } else {
            requestRuntimePermission()
        }
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY -> {
                    try {
                        val uri = data?.data
                        val bitmap = if (Build.VERSION.SDK_INT < 28) {
                            MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                        } else {
                            val source =
                                ImageDecoder.createSource(context?.contentResolver!!, uri!!)
                            ImageDecoder.decodeBitmap(source)
                        }
                        profileImage = context?.savePic(bitmap).toString()
                    } catch (e: Exception) {
                        context?.toast("Failed to load image")
                    }
                }
                CAMERA -> {
                    val mBitmap = data?.extras?.get("data") as Bitmap
                    profileImage = context?.savePic(mBitmap).toString()
                }
            }
        }
    }

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
