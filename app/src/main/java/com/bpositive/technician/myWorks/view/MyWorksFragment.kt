package com.bpositive.technician.myWorks.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bpositive.technician.BaseFragment
import com.bpositive.R
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.core.widget.CustomTextView
import com.bpositive.databinding.FragmentSettingsBinding
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.sessionNames
import kotlinx.android.synthetic.main.fragment_settings.*

/*
 *
 */

class MyWorksFragment : BaseFragment() {

    var viewModel: MyWorksViewModel? = null
    private lateinit var sessionManager: SessionManager

    override fun getTitle(): String {

        return "Settings Fragment"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onStart() {
        super.onStart()
        /*activity?.findViewById<AppCompatImageView>(R.id.headerImages)?.visibility = View.GONE
        activity?.findViewById<AppCompatImageView>(R.id.followersIcon)?.visibility = View.GONE
        activity?.findViewById<CustomTextView>(R.id.followersTv)?.visibility = View.GONE
        activity?.findViewById<CustomTextView>(R.id.nameTv)?.text = getString(R.string.my_works)
        activity?.findViewById<AppCompatImageView>(R.id.userImage)?.setBackgroundDrawable(null)
        Glide
            .with(this)
            .load(R.drawable.ic_setting_white)
            .apply(RequestOptions.circleCropTransform())
            .into(activity?.findViewById<AppCompatImageView>(R.id.userImage)!!)*/

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_works, container, false)
        viewModel =
            ViewModelProviders.of(this).get(MyWorksViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(context!!)

        if (sessionManager.getSession(sessionNames.USER_ID)!!.isNotEmpty()) {
            homePageImage.visibility = VISIBLE
            homePageHeader.visibility = VISIBLE
            homePageList.visibility = VISIBLE
        }
//        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

//        viewModel?.start()
//        startObserveResponse()
//        startObserveMessage()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun startObserveMessage() {

    }



    companion object {
        const val TAG = "CaseDetailsFragment"
        fun newInstanceTest() = MyWorksFragment()
    }

}