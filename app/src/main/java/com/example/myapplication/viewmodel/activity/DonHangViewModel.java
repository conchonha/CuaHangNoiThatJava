package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Model.DonDatHang;
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
public class DonHangViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<DonDatHang>> arrayListDonDatHang = new MutableLiveData<>();

    public DonHangViewModel(@NonNull Application application) {
        super(application);
    }

    public void getdatadonhangAdmin(){
        DataService dataService= APIServices.getService();
        Call<List<DonDatHang>> callback=dataService.getdataAlldonhangAdmin();
        callback.enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {
                Log.d("AAA","getdataall donhang admin: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<DonDatHang> arrayList= (ArrayList<DonDatHang>) response.body();
                    arrayListDonDatHang.postValue(arrayList);
                    Log.d("AAA", DangNhap.sharedPreferences.getInt("iduser",0)+"");
                }
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {

            }
        });
    }

    public void getdatadondathang() {
        DataService dataService= APIServices.getService();
        Call<List<DonDatHang>>call=dataService.getdatadonhang(DangNhap.sharedPreferences.getInt("iduser",0)+"");
        call.enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {
                Log.d("AAA","getdatadonhang: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<DonDatHang>arrayList= (ArrayList<DonDatHang>) response.body();
                    arrayListDonDatHang.postValue(arrayList);
                    Log.d("AAA",DangNhap.sharedPreferences.getInt("iduser",0)+"");
                }
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {

            }
        });
    }

}
