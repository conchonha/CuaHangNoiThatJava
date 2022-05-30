package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Activity.SanPham;
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
public class SanPhamViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Sanpham>>listSanPham = new MutableLiveData<>();
    public SanPhamViewModel(@NonNull Application application) {
        super(application);
    }

    public void getSanPhamId(String s) {
        DataService dataService= APIServices.getService();
        Call<List<Sanpham>> callback=dataService.getdatasanpham(s);
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                Log.d("AAA","getdatasanpham"+response.toString());
                if(response.isSuccessful()){
                    ArrayList<Sanpham> arrayList = (ArrayList<Sanpham>) response.body();
                    listSanPham.postValue(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {

            }
        });
    }
}
