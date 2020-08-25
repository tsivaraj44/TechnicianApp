package com.bpositive.technician.settings.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.R
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.core.widget.CustomTextView
import com.bpositive.databinding.FragmentSettingsBinding
import com.bpositive.technician.myWorks.view.MyWorksFragment
import com.bpositive.technician.settings.model.LanguageModel
import com.bpositive.technician.settings.model.SettingMenus
import com.bpositive.technician.settings.view.adapter.LanguageAdapter
import com.bpositive.technician.settings.view.adapter.SettingMenusAdapter
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.GridViewItems.GRID_ITEMS
import com.bpositive.technician.utils.MiddleDividerItemDecoration
import com.bpositive.technician.utils.sessionNames
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_settings.*

/*
 *
 */

class SettingsFragment : BaseFragment(), LanguageAdapter.OnItemClickListener,
    SettingMenusAdapter.HomePageOnItemClickListener {

    private lateinit var viewDataBinding: FragmentSettingsBinding
    var viewModel: MyWorksViewModel? = null
    private lateinit var languageAdapter: LanguageAdapter
    private lateinit var languageArrayList: ArrayList<LanguageModel>
    private var homeArrayList: ArrayList<SettingMenus.Details> = ArrayList()
    private lateinit var homePageAdapter: SettingMenusAdapter
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
        activity?.findViewById<AppCompatImageView>(R.id.headerImages)?.visibility = View.GONE
        activity?.findViewById<AppCompatImageView>(R.id.followersIcon)?.visibility = View.GONE
        activity?.findViewById<CustomTextView>(R.id.followersTv)?.visibility = View.GONE
        activity?.findViewById<CustomTextView>(R.id.nameTv)?.text = "Settings"
        activity?.findViewById<AppCompatImageView>(R.id.userImage)?.setBackgroundDrawable(null)
        Glide
            .with(this)
            .load(R.drawable.ic_setting_white)
            .apply(RequestOptions.circleCropTransform())
            .into(activity?.findViewById<AppCompatImageView>(R.id.userImage)!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProviders.of(this@SettingsFragment).get(MyWorksViewModel::class.java)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(context!!)

        if (sessionManager.getSession(sessionNames.USER_ID)!!.isNotEmpty()) {
            homePageImage.visibility = VISIBLE
            homePageHeader.visibility = VISIBLE
            homePageList.visibility = VISIBLE
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        languageList.apply {
            layoutManager = LinearLayoutManager(context)
            LinearLayoutManager.VERTICAL
            isNestedScrollingEnabled = true
        }
        // once we change grid view layout manager count means need to check adapter line no.38
        homePageList.apply {
            layoutManager = GridLayoutManager(context, GRID_ITEMS)
            GridLayoutManager.VERTICAL
            isNestedScrollingEnabled = true
            resources.getDimension(R.dimen.specing)
            val itemDecoration =
                MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.HORIZONTAL)
            itemDecoration.setDividerColor(
                ContextCompat.getColor(
                    context!!,
                    android.R.color.holo_red_dark
                )
            )
            // addItemDecoration(itemDecoration)

            val itemDecoration1 =
                MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.VERTICAL)
            itemDecoration1.setDividerColor(
                ContextCompat.getColor(
                    context!!,
                    android.R.color.holo_red_dark
                )
            )
            // addItemDecoration(itemDecoration1)
        }

        languageArrayList = ArrayList()

        var languageModel: LanguageModel = LanguageModel()
        languageModel.name = "English"
        languageModel.status = true
        languageArrayList.add(languageModel)

        /*var languageModel1: LanguageModel = LanguageModel()
        languageModel1.name = "Arabic"
        languageModel1.status = false
        languageArrayList.add(languageModel1)*/

        languageAdapter = LanguageAdapter(context!!, languageArrayList, this)
        languageList.adapter = languageAdapter

        /*nestedScrollViewLanguage.post(Runnable {
            // nestedScrollViewLanguage.scrollTo(0, 0)
        })*/
        homePageAdapter =
            SettingMenusAdapter(homeArrayList, this)
        homePageList.adapter = homePageAdapter



//        viewModel?.start()
//        startObserveResponse()
//        startObserveMessage()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun startObserveMessage() {


    }

    private fun startObserveResponse() {
        viewModel?.languageList?.observe(activity as MainActivity, androidx.lifecycle.Observer {
            Log.i("startObserveResponse", "--->>>>> " + it as ArrayList<LanguageModel>)
            languageArrayList = it as ArrayList<LanguageModel>
            languageAdapter.notifyDataSetChanged()
        })
        viewModel?.settingMenu?.observe(activity as MainActivity, androidx.lifecycle.Observer {
            homeArrayList = it as ArrayList<SettingMenus.Details>
            if (homePageAdapter.items.size == 0) {
                homePageAdapter =
                    SettingMenusAdapter(homeArrayList, this)
                homePageList.adapter = homePageAdapter
            }
        })

        viewModel?.toastMessage?.observe(activity as MainActivity, androidx.lifecycle.Observer {
            activity?.toast(it)
        })

    }

    /*
    * Language selection
    * */
    override fun languageItemClickListener(position: Int) {

        for (i in 0 until languageArrayList.size) {
            if (i == position) {
                var languageModel: LanguageModel = languageArrayList[position]
                languageModel.status = true
                languageArrayList.remove(languageModel)
                languageArrayList.add(languageModel)
            } else {
                var languageModel: LanguageModel = languageArrayList[position]
                languageModel.status = false
                languageArrayList.remove(languageModel)
                languageArrayList.add(languageModel)
            }
        }
        languageAdapter.notifyDataSetChanged()

        /*when (position) {
            0 -> {
                chooseLanguage(ENGLISH)
                activity?.recreate()
            }
            1 -> {
                chooseLanguage(ARABIC)
                activity?.recreate()
            }
        }*/
    }

    /*
    * app language selection
    * */


    override fun menuItemClickListener(position: Int) {
        for (i in 0 until homeArrayList.size) {
            var settingsMenu: SettingMenus.Details = homeArrayList[position]
            if (position == i) {
                if (settingsMenu.selected.equals("1")) {
                    settingsMenu.selected = "0"
                } else {
                    settingsMenu.selected = "1"
                }
                homeArrayList.set(homeArrayList.indexOf(settingsMenu), settingsMenu)
                homePageAdapter!!.notifyItemChanged(homeArrayList.indexOf(settingsMenu))
                viewModel?.settingMenu?.value = homeArrayList
            }
        }
    }

    companion object {
        const val TAG = "CaseDetailsFragment"
        fun newInstanceTest() = SettingsFragment()
    }

}