package com.bpositive.technician.myWorks.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bpositive.technician.myWorks.view.WorksFragment

class MyWorkViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    fun addMyWorks(workFrag: Fragment, title: String) {
        fragmentList.add(workFrag)
        titleList.add(title)
    }

    fun addMyWorksList(workFrag: List<WorksFragment>, title: List<String>) {
        fragmentList.addAll(workFrag)
        titleList.addAll(title)
    }

}