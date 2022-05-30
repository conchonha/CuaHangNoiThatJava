package com.example.myapplication.viewmodel.fragment;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Adapter.Adapter_RecyclerviewSanpham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.util.ArrayList;
import java.util.List;

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
public class Fragment5ViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Sanpham>> listSanPham = new MutableLiveData<>();

    public Fragment5ViewModel(@NonNull Application application) {
        super(application);
    }

    public void getdatasanpham() {
        DataService dataService= APIServices.getService();
        Call<List<Sanpham>> callback=dataService.getdataxiaomi();
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                Log.d("AAA","getdata huawei"+response.toString());
                if(response.isSuccessful()){
                    listSanPham.postValue((ArrayList<Sanpham>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {

            }
        });
    }
}
