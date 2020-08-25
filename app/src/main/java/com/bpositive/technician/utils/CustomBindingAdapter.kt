package com.bpositive.technician.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bpositive.R
import com.bpositive.technician.core.widget.MvvmImageView

class CustomBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("isVisible")
        fun showHide(view: View, show: Boolean) {
            Log.i("CustomBindingAdapter", "showHide : $show")
            view.visibility = if (show) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("visi")
        fun showHideProgressbar(view: ProgressBar, show: Boolean) {
            Log.i("CustomBindingAdapter", "showHideProgressbar : $show")
            view.visibility = if (show) View.VISIBLE else View.GONE
        }

//        @JvmStatic
//        @BindingAdapter("visibilityRecyclerView")
//        fun showHideRecyclerView(view: View, show: Boolean) {
//            Log.i("CustomBindingAdapter", "showHideRecyclerView : $show")
//            view.visibility = if (show) View.VISIBLE else View.GONE
//        }
//
//        @JvmStatic
//        @BindingAdapter("visibilityText")
//        fun showHideText(view: View, show: Boolean) {
//            Log.i("CustomBindingAdapter", "showHideText : $show")
//            view.visibility = if (show) View.VISIBLE else View.GONE
//        }

        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImage(view: MvvmImageView, url: String?) {
            Glide.with(view.context).load(url.toString()).into(view)
        }

        @JvmStatic
        @BindingAdapter("android:imageUrl")
        fun loadCircleImage(
            view: ImageView,
            url: String
        ) { // http://i.stack.imgur.com/mwIhm.png

            Glide // .apply(RequestOptions.circleCropTransform())
                .with(view.context)
                .load(url)
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("srcStatic")
        fun setImageViewResource(
            imageView: ImageView,
            resource: Boolean
        ) {
            imageView.setImageResource(R.drawable.ic_tick_blue)
            if (resource) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("srcStaticCheckBox")
        fun setCheckbox(
            imageView: ImageView,
            result: String
        ) {
            if (result.equals("1")) {
                imageView.setImageResource(R.drawable.ic_tick_orange)
            } else {
                imageView.setImageResource(R.drawable.ic_tick_orange_uncheck)
            }
        }
    }
}


