package com.bpositive.technician.myWorks.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bpositive.technician.myWorks.model.response.AdminMapListViewsResponse;
import com.bpositive.technician.myWorks.service.repository.AdminMapListViewsRepository;


public class AdminMapListViewsMainViewModel extends ViewModel {

    private MutableLiveData<AdminMapListViewsResponse> mutableListViewsLiveData = new MutableLiveData<>();
    private AdminMapListViewsRepository repository;

    public AdminMapListViewsMainViewModel() {
    }

    public void getAdminAllMapListViewsAPI(Context context) {

        if(repository ==null){
            repository = AdminMapListViewsRepository.getInstance();
        }
        mutableListViewsLiveData = repository.getAdminMapAllListViewsService(context);

    }

    public LiveData<AdminMapListViewsResponse> getAdminAllMapListViewsResponseLiveData(){
        return mutableListViewsLiveData;
    }

}
