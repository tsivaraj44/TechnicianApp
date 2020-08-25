package com.bpositive.technician.myWorks.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bpositive.R;
import com.bpositive.technician.BaseFragment;
import com.bpositive.technician.MainActivity;
import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;
import com.bpositive.technician.myWorks.view.adapter.AdminMapListViewsAdapter;
import com.bpositive.technician.myWorks.viewModel.AdminMapListViewsMainViewModel;
import com.bpositive.technician.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class AdminMapListViewPagerFragment extends BaseFragment implements View.OnClickListener{

    View rootView;
    private LinearLayout parentLayout;
    private RecyclerView recyclerviewAdminAlerts;
    private TextView empty_view;
    private AdminMapListViewsAdapter adminMapListViewsAdapter;
    private AdminMapListViewsMainViewModel viewModel;
    ArrayList<AdminMapListViewsResponse.AllDevices> data = new ArrayList();

    private int deviceItemPos;
    private ArrayList<AdminMapListViewsResponse.AllDevices> allDeviceList = new ArrayList<>();




    public static AdminMapListViewPagerFragment newInstance(String tabTitle, int deviceItemPos,ArrayList<AdminMapListViewsResponse.AllDevices> allDeviceList) {
        AdminMapListViewPagerFragment fragment = new AdminMapListViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_ALL_DEVICES_LIST_POS, deviceItemPos);
        args.putSerializable(Constants.EXTRA_ALL_DEVICES_LIST, allDeviceList);
        Log.d("tab title: ", tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_map_listview, container, false);
        if(getArguments()!=null) {
            deviceItemPos = getArguments().getInt(Constants.EXTRA_ALL_DEVICES_LIST_POS);
            allDeviceList = (ArrayList<AdminMapListViewsResponse.AllDevices>) getArguments().getSerializable(Constants.EXTRA_ALL_DEVICES_LIST);
        }

        viewModel = new ViewModelProvider(this).get(AdminMapListViewsMainViewModel.class);

        recyclerviewAdminAlerts = rootView.findViewById(R.id.recycler_view_spc_map_listview_admin);
        empty_view = rootView.findViewById(R.id.empty_view);
        parentLayout=rootView.findViewById(R.id.lay_recycler_view_spc_map_listview);

        proceedToShowList();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void proceedToShowList() {
        recyclerviewAdminAlerts.setVisibility(View.VISIBLE);
        empty_view.setVisibility(View.GONE);

        if(allDeviceList!=null && deviceItemPos <= allDeviceList.size()-1) {
            ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> deviceListViewItem = allDeviceList.get(deviceItemPos).deviceListViewItem;
            prepareAdapter(deviceListViewItem);
        } else {
            showMessage("Empty List");
        }

    }

    /*private void callGetAllAdminMapListAPI(){

        showProgressDialog();
        viewModel.getAdminAllMapListViewsAPI(getContext());
        observeToGetAllAdminMapListAPIResponse();
    }*/

    /*private void observeToGetAllAdminMapListAPIResponse(){
        //Sumit
        viewModel.getAdminAllMapListViewsResponseLiveData().observe(getViewLifecycleOwner(), new Observer<AdminMapListViewsResponse>() {
            @Override
            public void onChanged(AdminMapListViewsResponse adminMapListViewResponse) {

                hideProgressDialog();

                if(adminMapListViewResponse!=null) {
                    if(adminMapListViewResponse.status ==1) { // Get all vehicle list successfully
                        recyclerviewAdminAlerts.setVisibility(View.VISIBLE);
                        empty_view.setVisibility(View.GONE);
                        prepareAdapter(adminMapListViewResponse.allDevicesTabs);
                    } else {
                        recyclerviewAdminAlerts.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                        empty_view.setText(adminMapListViewResponse.message.toString());
                        showMessage(adminMapListViewResponse.message.toString());
                    }
                } else {
                    recyclerviewAdminAlerts.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                    //empty_view.setText(adminNotificationResponse.message.toString());
                    //showMessage(adminNotificationResponse.message.toString());
                }
            }
        });
    }*/

    private void prepareAdapter(ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> deviceListViewItem) {
        adminMapListViewsAdapter=new AdminMapListViewsAdapter(deviceListViewItem, new AdminMapListViewsAdapter.AdminMapListViewsItemListener() {
            @Override
            public void onAdminMapListViewsItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerviewAdminAlerts.setLayoutManager(manager);
        recyclerviewAdminAlerts.setHasFixedSize(true);
        recyclerviewAdminAlerts.setAdapter(adminMapListViewsAdapter);
    }

    /*private void prepareAdapter(ArrayList<AdminMapListViewsResponse.AllDevices> allDevicesList)
    {
        data=allDevicesList;
        adminMapListViewsAdapter=new AdminMapListViewsAdapter(data, new AdminMapListViewsAdapter.AdminMapListViewsItemListener() {
            @Override
            public void onAdminMapListViewsItemClick(int position) {

            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerviewAdminAlerts.setLayoutManager(manager);
        recyclerviewAdminAlerts.setHasFixedSize(true);
        recyclerviewAdminAlerts.setAdapter(adminMapListViewsAdapter);
    }*/

    private void showMessage(String displayMessage){
//        ShowToast(displayMessage)
    }

    @Override
    public void onClick(View view) {

    }

    @NotNull
    @Override
    protected String getTitle() {
        return "WORKS";
    }

    @Override
    protected boolean getShowHomeToolbar() {
        return false;
    }
}
