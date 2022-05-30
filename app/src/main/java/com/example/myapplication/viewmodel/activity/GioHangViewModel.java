package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Adapter.Adapter_Giohang;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.text.DecimalFormat;
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
public class GioHangViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<com.example.myapplication.Model.GioHang>> listGioHang = new MutableLiveData<>();
    public MutableLiveData<Boolean>checkDelete = new MutableLiveData<>();
    public MutableLiveData<Boolean>checkTang = new MutableLiveData<>();
    public MutableLiveData<Boolean>checkGiam = new MutableLiveData<>();

    public GioHangViewModel(@NonNull Application application) {
        super(application);
    }

    public void getdatagiohang() {
        DataService dataService= APIServices.getService();
        Call<List<GioHang>> callback=dataService.getdatagiohang(DangNhap.sharedPreferences
                .getInt("iduser",0)+"");
        callback.enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<com.example.myapplication.Model.GioHang>> call, Response<List<GioHang>> response) {
                Log.d("AAA","getdata giohang: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<GioHang> arrayList= (ArrayList<com.example.myapplication.Model.GioHang>) response.body();
                    listGioHang.postValue(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<com.example.myapplication.Model.GioHang>> call, Throwable t) {

            }
        });
    }

    public void Deletegiohang(String id){
        DataService dataService=APIServices.getService();
        Call<String>call=dataService.deletegiohang(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","deletegiohang: "+response.toString());
                if(response.isSuccessful()){
                    checkDelete.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void tang(String id){
        DataService dataService=APIServices.getService();
        Call<String>call=dataService.tang(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","tanggiohang: "+response.toString());
                if(response.isSuccessful()){
                    checkTang.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void giam(String id){
        DataService dataService=APIServices.getService();
        Call<String>call=dataService.giam(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","tanggiohang: "+response.toString());
                if(response.isSuccessful()){
                    checkGiam.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
