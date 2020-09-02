package com.bpositive.technician.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bpositive.R
import com.bpositive.technician.utils.ImageConstants.CAMERA
import com.bpositive.technician.utils.ImageConstants.GALLERY
import com.bpositive.technician.utils.ImageConstants.IMAGE_DIRECTORY
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun Int.isSuccess() = this == 1

fun String.isSuccess() = this == "Success"

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.ShowToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.makeCircularImage(drawable: String) {
    Glide
        .with(context)
        .load(drawable)
        .circleCrop()
        .into(this as ImageView)
}

fun View.loadImage(url: String) {
    Glide
        .with(context)
        .load(url)
        .into(this as AppCompatImageView)
}

fun String.replaceComma() = this.replace(",", "").toLong()

fun String.convertDateToLong(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = dateFormat.parse(this)
    return date.time
}

fun View.showKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
    primaryNavigationFragment?.childFragmentManager?.fragments?.first()

fun Context.showAlert(msg: String, confirmation: OnSuccess<Boolean>) {
    AlertDialog.Builder(this).apply {
        setMessage(msg)
        setCancelable(true)
        setPositiveButton("Yes") { dialog, which ->
            confirmation.invoke(true)
            dialog.dismiss()
        }
        setNegativeButton("No") { dialog, which ->
            dialog.cancel()
        }
    }.show().also {
        it.findViewById<TextView>(android.R.id.message)?.textSize = 26f
        it.getButton(DialogInterface.BUTTON_POSITIVE).textSize = 22f
        it.getButton(DialogInterface.BUTTON_NEGATIVE).textSize = 22f
    }
}

fun Context.showMoveTo(confirmation: OnSuccess<Int>) {
    AlertDialog.Builder(this).apply {
        setMessage("Move To")
        setCancelable(true)
        setPositiveButton("Pending") { dialogInterface, i ->
            confirmation.invoke(TravelStatus.PENDING)
            dialogInterface.dismiss()
        }
        setNeutralButton("Completed") { dialogInterface, i ->
            confirmation.invoke(TravelStatus.COMPLETED)
            dialogInterface.dismiss()
        }
    }.show()
}

fun Fragment.showDialogToPick() {
    val pickDialog = android.app.AlertDialog.Builder(this.context)
    pickDialog.setTitle(resources.getString(R.string.choose_an_action))
    val pictureSelection = arrayOf(
        resources.getString(R.string.select_from_gallery),
        resources.getString(R.string.select_take_photo)
    )
    pickDialog.setItems(pictureSelection) { dialog, which ->
        when (which) {
            0 -> choosePhotoFromGallery()
            1 -> takePhotoFromCamera()
        }
    }
    pickDialog.show()
}

fun Fragment.choosePhotoFromGallery() {
    startActivityForResult(
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
        GALLERY
    )
}

fun Fragment.takePhotoFromCamera() {
    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA)
}

fun Context.savePic(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    //  val directory = File(Environment.getExternalStorageState().toString() + IMAGE_DIRECTORY)  // getExternalStorageDirectory()
    val directory = File(this.filesDir, IMAGE_DIRECTORY)
    if (!directory.exists()) directory.mkdirs()
    try {
        val file = File(directory, ((Calendar.getInstance().timeInMillis).toString() + ".jpeg"))
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        MediaScannerConnection.scanFile(this, arrayOf(file.path), arrayOf("image/jpeg"), null)
        fileOutputStream.close()
        this.toast(resources.getString(R.string.picture_selected))
        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}