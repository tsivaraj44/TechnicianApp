package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.myWorks.view.adapter.MyWorkViewPagerAdapter
import com.bpositive.technician.utils.TravelStatus.COMPLETED
import com.bpositive.technician.utils.TravelStatus.IN_PROGRESS
import com.bpositive.technician.utils.TravelStatus.PENDING
import com.bpositive.technician.utils.TravelStatus.UP_COMING
import com.bpositive.technician.utils.sessionNames
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_my_works.*
import kotlinx.android.synthetic.main.fragment_settings.*

class MyWorksFragment : BaseFragment() {

    private lateinit var sessionManager: SessionManager

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
        fragList.add(WorksFragment.newInstance(UP_COMING))
        fragList.add(WorksFragment.newInstance(IN_PROGRESS))
        fragList.add(WorksFragment.newInstance(PENDING))
        fragList.add(WorksFragment.newInstance(COMPLETED))

        val fragTitle = mutableListOf<String>()
        fragTitle.add("Upcoming")
        fragTitle.add("In-Progress")
        fragTitle.add("Pending")
        fragTitle.add("Completed")

        val adapter = MyWorkViewPagerAdapter(childFragmentManager)
        adapter.addMyWorksList(fragList, fragTitle)
        /*adapter.addMyWorks(upComingFrag, "Upcoming")
        adapter.addMyWorks(inProgressFrag, "In-Progress")
        adapter.addMyWorks(pendingFrag, "Pending")
        adapter.addMyWorks(completedFrag, "Completed")*/
        vpMyWork.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /*for (pos in 0 until adapter.count)
                    adapter.getItem(pos).setMenuVisibility(position == pos)*/
            }

            override fun onPageSelected(position: Int) {
                fragList[position].getMyWork()
                println("GET__________________$position")
            }
        })

        vpMyWork.adapter = adapter
        vpMyWork.currentItem = 0
        tbMyWork.setupWithViewPager(vpMyWork)

//        adapter.addMyWorksList(fragList, fragTitle)
//        adapter.notifyDataSetChanged()
        //  TODO  ::  ::  Need to check
        //  vpMyWork?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tbMyWork))

        /*vpMyWork.setOnTouchListener { view, motionEvent ->
            true
        }*/

        /*tbMyWork.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null)
                    vpMyWork.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {}
        })*/

    }

}