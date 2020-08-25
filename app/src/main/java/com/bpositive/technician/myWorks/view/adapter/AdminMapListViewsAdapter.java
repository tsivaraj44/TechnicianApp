package com.bpositive.technician.myWorks.view.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bpositive.R;
import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;

import java.util.ArrayList;

public class AdminMapListViewsAdapter extends RecyclerView.Adapter<AdminMapListViewsAdapter.AdminMapListViewHolder> {
    private ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> listItems;

    private AdminMapListViewsAdapter.AdminMapListViewsItemListener itemListener;

    public AdminMapListViewsAdapter(ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> listItems, AdminMapListViewsAdapter.AdminMapListViewsItemListener itemListener) {
        this.listItems = listItems;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public AdminMapListViewsAdapter.AdminMapListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminMapListViewsAdapter.AdminMapListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_map_lisview_card_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final AdminMapListViewsAdapter.AdminMapListViewHolder holder, final int position) {

        final AdminMapListViewsResponse.AllDevices.DeviceListItem data = listItems.get(position);

        holder.txtMapListViewsTitle.setText(data.deviceLocation);
        holder.txtMapListViewsStatus.setText(data.deviceStatus);
        holder.txtMapListViewsStatusInfo.setText(data.deviceStatusInfo);
        holder.btnAction.setText(data.deviceBtnActionMessage);

        // Show Video img view based on URL
        if (!TextUtils.isEmpty(data.deviceVideoUrl)) {
            holder.imgVideo.setVisibility(View.VISIBLE);
        } else {
            holder.imgVideo.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.deviceImgUrl)) {
            holder.imgPhoto.setVisibility(View.VISIBLE);
        } else {
            holder.imgPhoto.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.deviceMapNavigation)) {
            holder.imgMapListView.setVisibility(View.VISIBLE);
        } else {
            holder.imgMapListView.setVisibility(View.GONE);
        }

        holder.layAdminItemsMapListViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.layExpandDetails.getVisibility() == View.VISIBLE) {
                    holder.layExpandDetails.setVisibility(View.GONE);
                } else {
                    holder.layExpandDetails.setVisibility(View.VISIBLE);
                }

                if (itemListener != null) {
                    itemListener.onAdminMapListViewsItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class AdminMapListViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout layAdminItemsMapListViews;
        private LinearLayout layMapListViewsItems;
        private LinearLayout layExpandDetails;
        private TextView txtMapListViewsTitle;
        private TextView txtMapListViewsStatus;
        private TextView txtMapListViewsStatusInfo;
        private ImageView imgPhoto;
        private ImageView imgVideo;
        private ImageView imgMapListView;
        private TextView btnAction;

        public AdminMapListViewHolder(@NonNull View itemView) {
            super(itemView);
            layAdminItemsMapListViews = itemView.findViewById(R.id.item_root_admin_map_listview);
            layMapListViewsItems = itemView.findViewById(R.id.lay_map_listview_items);
            layExpandDetails = itemView.findViewById(R.id.lay_expand_map_listview_details);
            txtMapListViewsTitle = itemView.findViewById(R.id.txt_map_listview_title);
            txtMapListViewsStatus = itemView.findViewById(R.id.txt_map_listview_status);
            txtMapListViewsStatusInfo = itemView.findViewById(R.id.txt_map_listview_status_info);

            imgPhoto = itemView.findViewById(R.id.img_map_listview_photo);
            imgVideo = itemView.findViewById(R.id.img_map_listview_video);
            imgMapListView = itemView.findViewById(R.id.img_map_navigation);
            btnAction = itemView.findViewById(R.id.txt_map_listview_action);


            layMapListViewsItems.setVisibility(View.VISIBLE);
            layExpandDetails.setVisibility(View.GONE);
        }
    }

    public ArrayList<AdminMapListViewsResponse.AllDevices.DeviceListItem> getListItems() {
        return listItems;
    }

    public interface AdminMapListViewsItemListener {
        void onAdminMapListViewsItemClick(int position);
    }
}
