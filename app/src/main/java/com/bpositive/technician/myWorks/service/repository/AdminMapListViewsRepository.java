package com.bpositive.technician.myWorks.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bpositive.R;
import com.bpositive.technician.core.mockData.ConvertRawJsonToGSON;
import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;
import com.google.gson.Gson;

public class AdminMapListViewsRepository {


    private static AdminMapListViewsRepository userNotificationRepository;

//    private AdminMapListViewsApi apiService;

    public static AdminMapListViewsRepository getInstance(){
        if(userNotificationRepository ==null)
            userNotificationRepository = new AdminMapListViewsRepository();

        return userNotificationRepository;
    }

    public AdminMapListViewsRepository(){
//        apiService = API.createService(AdminMapListViewsApi.class);

    }

    public MutableLiveData<AdminMapListViewsResponse> getAdminMapAllListViewsService(Context context){
        final MutableLiveData<AdminMapListViewsResponse> responseGetAllAdminMapListViewsMutableLiveData = new MutableLiveData<>();

        // API calling

        responseGetAllAdminMapListViewsMutableLiveData.setValue(mockAdminMapListViewsResponse(context));

        return responseGetAllAdminMapListViewsMutableLiveData;
    }

    private ConvertRawJsonToGSON mConvertRawJsonToGSON;
    private AdminMapListViewsResponse mockAdminMapListViewsResponse(Context context) {
        if(mConvertRawJsonToGSON==null){
            mConvertRawJsonToGSON = new ConvertRawJsonToGSON(context);
        }

        String json = mConvertRawJsonToGSON.getTransitJsonDataFromLocalFile(R.raw.my_works);

        Gson gson = new Gson();
        AdminMapListViewsResponse mapResponse = gson.fromJson(json, AdminMapListViewsResponse.class);
        Log.d("TAG", "AdminMapListViewsResponse: mapResponse "+mapResponse.message);

        return mapResponse;
    }
}
