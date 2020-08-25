package com.bpositive.technician.myWorks.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;
import com.bpositive.technician.myWorks.view.fragment.AdminMapListViewPagerFragment;

import java.util.ArrayList;


public class AdminMapListViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private ArrayList<String> tabTitle;
    private ArrayList<String> tabNumber;
    private ArrayList<AdminMapListViewsResponse.AllDevices> allDeviceList;

    public AdminMapListViewPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabTitle, ArrayList<String> tabNumber, ArrayList<AdminMapListViewsResponse.AllDevices> allDeviceList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabTitle = tabTitle;
        this.tabNumber = tabNumber;
        this.allDeviceList = allDeviceList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AdminMapListViewPagerFragment.newInstance(tabTitle.get(position), position, allDeviceList);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}