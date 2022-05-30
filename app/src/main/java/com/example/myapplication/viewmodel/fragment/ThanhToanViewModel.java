package com.example.myapplication.viewmodel.fragment;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Activity.HoaDon;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class ThanhToanViewModel extends AndroidViewModel {
    public MutableLiveData<String> thanhToanID = new MutableLiveData<>();

    public ThanhToanViewModel(@NonNull Application application) {
        super(application);
    }

    public void thanhToan(String iduser, String toString, String toString1, String toString2, String toString3) {
        DataService dataService = APIServices.getService();
        Call<String> call = dataService.postgiohang(iduser,
                toString,
                toString1,
                toString2,
                toString3
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA", "loi: " + response.toString());
                if (response.isSuccessful()) {
                    String id = "";
                    id = response.body();
                    thanhToanID.postValue(id);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
