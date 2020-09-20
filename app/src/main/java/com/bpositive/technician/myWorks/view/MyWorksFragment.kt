package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.myWorks.view.adapter.MyWorkViewPagerAdapter
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.TravelStatus.COMPLETED
import com.bpositive.technician.utils.TravelStatus.IN_PROGRESS
import com.bpositive.technician.utils.TravelStatus.PENDING
import com.bpositive.technician.utils.TravelStatus.UP_COMING
import com.bpositive.technician.utils.sessionNames
import kotlinx.android.synthetic.main.fragment_my_works.*
import kotlinx.android.synthetic.main.fragment_settings.*

class MyWorksFragment : BaseFragment() {

    private lateinit var sessionManager: SessionManager
    val viewModel: MyWorksViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, MyWorksViewModel.Factory(context))
                .get(MyWorksViewModel::class.java)
        }
    }

    override fun getTitle(): String {
        return "My Works"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onDestroyView() {
        (activity as MainActivity).enableScrollingBehaviour()
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).removeScrollingBehaviour()
        return inflater.inflate(R.layout.fragment_my_works, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(context!!)

        if (sessionManager.getSession(sessionNames.USER_ID)!!.isNotEmpty()) {
            homePageImage.visibility = VISIBLE
            homePageHeader.visibility = VISIBLE
            homePageList.visibility = VISIBLE
        }

        val fragList = mutableListOf<WorksFragment>()
        fragList.add(WorksFragment.newInstance(IN_PROGRESS, viewModel))
        fragList.add(WorksFragment.newInstance(PENDING, viewModel))
        fragList.add(WorksFragment.newInstance(UP_COMING, viewModel))
        fragList.add(WorksFragment.newInstance(COMPLETED, viewModel))

        val fragTitle = mutableListOf<String>()
        fragTitle.add("In-Progress")
        fragTitle.add("Pending")
        fragTitle.add("Upcoming")
        fragTitle.add("Completed")

        val adapter = MyWorkViewPagerAdapter(childFragmentManager)
        adapter.addMyWorksList(fragList, fragTitle)
        vpMyWork.adapter = adapter

        vpMyWork.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                fragList[position].getMyWork()
            }
        })

        tbMyWork.setupWithViewPager(vpMyWork)
    }

}