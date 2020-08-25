package com.bpositive.technician.core.widget

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bpositive.R


class LoginDialog : DialogFragment {

    lateinit var dialogTitle: CustomTextView
    lateinit var proceed: Button
    lateinit var logoImage: ImageView


    public lateinit var customDialogListener: CustomDialogListener

    interface CustomDialogListener {
        fun onCustomDialogListener()
    }

    @SuppressLint("ValidFragment")
    constructor(context: CustomDialogListener) {
        customDialogListener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        dialog!!.window
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val v: View = inflater.inflate(R.layout.dialog_no_login, container, false)
        dialogTitle = v.findViewById<View>(R.id.titleTv) as CustomTextView
        proceed = v.findViewById<Button>(R.id.proceedBt)
        logoImage = v.findViewById<ImageView>(R.id.logoImage)

        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        Glide
//            .with(this)
//            .load("https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg")
//            .apply(RequestOptions.circleCropTransform())
//            .into(logoImage)

        proceed.setOnClickListener({
            customDialogListener.onCustomDialogListener()
        })

    }
}