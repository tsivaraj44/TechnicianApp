package com.bpositive.technician.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
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
import com.bpositive.technician.utils.ImageConstants.CHOOSE_VIDEO
import com.bpositive.technician.utils.ImageConstants.GALLERY
import com.bpositive.technician.utils.ImageConstants.IMAGE_DIRECTORY
import com.bpositive.technician.utils.ImageConstants.TAKE_VIDEO
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

fun View.makeCircularImage(drawable: String, placeHolder: Int? = null) {
    Glide
        .with(context)
        .load(drawable)
        .circleCrop()
        .placeholder(placeHolder ?: R.color.white)
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
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
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

fun Fragment.showDialogToPickVideo() {
    val pickDialog = android.app.AlertDialog.Builder(this.context)
    pickDialog.setTitle(resources.getString(R.string.choose_an_action))
    val pictureSelection = arrayOf(
        resources.getString(R.string.select_video_from_gallery),
        resources.getString(R.string.select_take_video)
    )
    pickDialog.setItems(pictureSelection) { dialog, which ->
        when (which) {
            0 -> chooseVideoFromGallery()
            1 -> takeVideo()
        }
    }
    pickDialog.show()
}

fun Fragment.chooseVideoFromGallery() {
    /*startActivityForResult(
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI),
        VIDEO_FILE
    )*/

    val intent = Intent()
    intent.type = "video/*"
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(Intent.createChooser(intent, "Select a Video "), CHOOSE_VIDEO)
}

/*
private File createImageFile() throws IOException {
    // Create an image file name
    String imageFileName = "myvideo";
    File storageDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM), "Camera");
    File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".mp4",         /* suffix */
            storageDir      /* directory */
    );

    // Save a file: path for use with ACTION_VIEW intents
    Log.v("myTag","FAB create image");
    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
    return image;
}
*/

fun Fragment.takeVideo() {
    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
    if (intent.resolveActivity(context?.packageManager!!) != null) {
        startActivityForResult(intent, TAKE_VIDEO)
    }
    /*try {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val videoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            FileProvider.getUriForFile(
                context!!,
                context?.packageName + ".provider",
                context?.getOutputMediaFile(MEDIA_TYPE_VIDEO)!!
            )
        } else {
            Uri.fromFile(context?.getOutputMediaFile(MEDIA_TYPE_VIDEO))
        }
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)  // set video quality
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri) // set the image file name
      //  intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5)
        startActivityForResult(intent, TAKE_VIDEO)
    } catch (e: Exception) {
        println("GET___________ERROR_________$e")
    }*/
}

fun Context.getOutputMediaFile(mediaTypeVideo: Int): File {
    val directory = File(this.filesDir, IMAGE_DIRECTORY)
    if (!directory.exists()) directory.mkdirs()
    var file = File(directory.path)
    try { // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        if (mediaTypeVideo == MEDIA_TYPE_VIDEO) {
            file = File(directory.path + File.separator + "VID_" + timeStamp + ".mp4", ".mp4")
            file.createNewFile()
        }
        this.toast(resources.getString(R.string.video_saved_to) + " " + file.absolutePath)
        return file
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file
}