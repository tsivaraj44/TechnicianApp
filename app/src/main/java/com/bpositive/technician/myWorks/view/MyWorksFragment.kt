package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.myWorks.view.adapter.MyWorkViewPagerAdapter
import com.bpositive.technician.utils.TravelStatus.COMPLETED
import com.bpositive.technician.utils.TravelStatus.IN_PROGRESS
import com.bpositive.technician.utils.TravelStatus.PENDING
import com.bpositive.technician.utils.TravelStatus.UP_COMING
import com.bpositive.technician.utils.sessionNames
import kotlinx.android.synthetic.main.fragment_my_works.*
import kotlinx.android.synthetic.main.fragment_settings.*

class MyWorksFragment : BaseFragment() {

    private lateinit var sessionManager: SessionManager

    override fun getTitle(): String {
        return "Settings Fragment"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_my_works, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(context!!)

        if (sessionManager.getSession(sessionNames.USER_ID)!!.isNotEmpty()) {
            homePageImage.visibility = VISIBLE
            homePageHeader.visibility = VISIBLE
            homePageList.visibility = VISIBLE
        }

        val adapter = MyWorkViewPagerAdapter(childFragmentManager)
        adapter.addMyWorks(WorksFragment.newInstance(UP_COMING), "Upcoming")
        adapter.addMyWorks(WorksFragment.newInstance(IN_PROGRESS), "In-Progress")
        adapter.addMyWorks(WorksFragment.newInstance(PENDING), "Pending")
        adapter.addMyWorks(WorksFragment.newInstance(COMPLETED), "Completed")
        vpMyWork.adapter = adapter
        tbMyWork.setupWithViewPager(vpMyWork)

    }

}