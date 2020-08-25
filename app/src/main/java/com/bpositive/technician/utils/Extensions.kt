package com.bpositive.technician.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

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

fun View.loadImage(url:String){
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
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun FragmentManager.getCurrentNavigationFragment():Fragment? =
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