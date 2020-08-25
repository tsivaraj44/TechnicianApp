package com.bpositive.technician.myWorks.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.bpositive.R;
import com.bpositive.technician.BaseFragment;
import com.bpositive.technician.MainActivity;
import com.bpositive.technician.core.widget.CustomTextView;
import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;
import com.bpositive.technician.myWorks.view.adapter.AdminMapListViewPagerAdapter;
import com.bpositive.technician.myWorks.viewModel.AdminMapListViewsMainViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * To show all the Map List Views
 */
public class MyWorkFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();

    private View rootView;
    private AdminMapListViewsMainViewModel viewModel;

    private TabLayout tab;
    private ViewPager viewPager;
    private ArrayList<String> tabTitle = new ArrayList<>();
    private ArrayList<String> tabNumber = new ArrayList<>();
    private AdminMapListViewPagerAdapter adapter;

   private View v;
   private TextView tv1,tv2;
   private LinearLayout llNumber;
   private TabLayout.Tab Tab;
   private int position;
   private int tab_position=1;
   private boolean first=false;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_map_all_list_view_main, container, false);
        tab = rootView.findViewById(R.id.tabs);
        viewPager = rootView.findViewById(R.id.viewPager);
        tab.setupWithViewPager(viewPager);


        /*tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabnew) {
                position = tabnew.getPosition();
                tab_position = tab.getSelectedTabPosition();

                LinearLayout mTabsLinearLayout = ((LinearLayout)tabnew.getCustomView());
                if(mTabsLinearLayout!=null){
                    TextView txtTitle = mTabsLinearLayout.findViewById(R.id.txt_title);
                    LinearLayout layCount = mTabsLinearLayout.findViewById(R.id.llNumber);
                    txtTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    layCount.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_circle_bg_white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                Log.d(TAG, "tabsss onTabUnselected: "+tab.getPosition());
                LinearLayout mTabsLinearLayout = ((LinearLayout)tab.getCustomView());
                if(mTabsLinearLayout!=null){
                    TextView txtTitle = mTabsLinearLayout.findViewById(R.id.txt_title);
                    LinearLayout layCount = mTabsLinearLayout.findViewById(R.id.llNumber);
                    txtTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_light_corner));
                    layCount.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.drawable_circle_light_blue));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });*/


        viewModel = new ViewModelProvider(this).get(AdminMapListViewsMainViewModel.class);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callAdminListViewsAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void callAdminListViewsAPI() {
        viewModel.getAdminAllMapListViewsAPI(getContext());
        observeToGetAllAdminListViewsAPIResponse();
    }

    private void observeToGetAllAdminListViewsAPIResponse() {
        viewModel.getAdminAllMapListViewsResponseLiveData().observe(getViewLifecycleOwner(), new Observer<AdminMapListViewsResponse>() {
            @Override
            public void onChanged(AdminMapListViewsResponse response) {

//                showMessage(response.message);

                if (response != null) {
                    if (response.status == 1) {
                        handleAllListViews(response);
                    } else {
                        showMessage(response.message);
                    }
                } else {
                    showMessage(getResources().getString(R.string.something_went_wrong));
                }
            }
        });
    }

    private void showMessage(String displayMessage){
//        CommonUtils.showToast(getActivity(),displayMessage);
    }

    private void handleAllListViews(AdminMapListViewsResponse responseAllLstViews) {
        // HANDLE RESPONSE HERE

        ArrayList<AdminMapListViewsResponse.AllDevices> allDeviceList = new ArrayList<>(responseAllLstViews.allDevicesTabs);

        Log.d(TAG, "allDeviceTabs: "+allDeviceList.size());

        /*for (AdminMapListViewsResponse.AllDevices tabs: allDeviceList) {
            Log.d(TAG, "allDeviceTabs: deviceName "+tabs.deviceName+" id: "+tabs.deviceId);

            ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> listViewItems = new ArrayList<>(tabs.deviceListViewItem);

            Log.d(TAG, "allDeviceTabs: listViewItems "+listViewItems.size());

        }*/

        int allDeviceLen = allDeviceList.size();
        for (int i = 0; i < allDeviceLen; i++) {
            tab.addTab(tab.newTab().setText(allDeviceList.get(i).deviceName));
            tabTitle.add(allDeviceList.get(i).deviceName);
            tabNumber.add(String.valueOf(allDeviceList.get(i).deviceListViewItem.size()));

        }

        adapter = new AdminMapListViewPagerAdapter
                (getChildFragmentManager(), tab.getTabCount(), tabTitle,tabNumber, allDeviceList);

        viewPager.setAdapter(adapter);

        for (int i = 0; i < allDeviceLen; i++) {

            Tab = tab.getTabAt(i);
            if (Tab != null) {
                Tab.setCustomView(getTabView(allDeviceList.get(i).deviceName, String.valueOf(allDeviceList.get(i).deviceListViewItem.size())));
            }
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabnew) {
                position = tabnew.getPosition();
                tab_position = tab.getSelectedTabPosition();

                LinearLayout mTabsLinearLayout = ((LinearLayout)tabnew.getCustomView());
                if(mTabsLinearLayout!=null){
                    TextView txtTitle = mTabsLinearLayout.findViewById(R.id.txt_title);
                    LinearLayout layCount = mTabsLinearLayout.findViewById(R.id.llNumber);
                    txtTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
//                    layCount.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_circle_bg_white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                Log.d(TAG, "tabsss onTabUnselected: "+tab.getPosition());
                LinearLayout mTabsLinearLayout = ((LinearLayout)tab.getCustomView());
                if(mTabsLinearLayout!=null){
                    TextView txtTitle = mTabsLinearLayout.findViewById(R.id.txt_title);
                    LinearLayout layCount = mTabsLinearLayout.findViewById(R.id.llNumber);
                    txtTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_blue));
//                    layCount.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.drawable_circle_light_blue));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    private View getTabView(String s1,String s2) {
        v = LayoutInflater.from(getContext()).inflate(R.layout.custom_my_works_tab, null);
        tv1 = v.findViewById(R.id.txt_title);
        tv2 = v.findViewById(R.id.txt_no);
        llNumber = v.findViewById(R.id.llNumber);
        tab_position=tab.getSelectedTabPosition();
        tv1.setText(s1);
        tv2.setText(s2);

        if(!first) {
            if (tab.getSelectedTabPosition() == 0) {
                tv1.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
//                llNumber.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_circle_bg_white));
            }
            first = true;
        }

        /*if(!first) {
            if (position == tab_position) {

                tv1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    llNumber.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.drawable_circle_bg_white));
                } else {
                    llNumber.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.drawable_circle_bg_white));
                }
            }
            first = true;
        }*/
        return v;
    }

    @NotNull
    @Override
    protected String getTitle() {
        return "Works Title";
    }

    @Override
    protected boolean getShowHomeToolbar() {
        return true;
    }

}



