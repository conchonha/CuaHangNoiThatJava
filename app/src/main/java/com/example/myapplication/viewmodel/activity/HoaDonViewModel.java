package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Model.HoaDon;
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
public class HoaDonViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<HoaDon>> listHoaDon = new MutableLiveData<>();

    public HoaDonViewModel(@NonNull Application application) {
        super(application);
    }

    public void getHoaDon(String iddonhang) {
        DataService dataService= APIServices.getService();
        Call<List<HoaDon>> call=dataService.getAlldatadonhang(iddonhang);
        call.enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<com.example.myapplication.Model.HoaDon>> call, Response<List<HoaDon>> response) {
                Log.d("AAA","HoaDon: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<HoaDon> arrayList= (ArrayList<com.example.myapplication.Model.HoaDon>) response.body();
                    listHoaDon.postValue(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<com.example.myapplication.Model.HoaDon>> call, Throwable t) {

            }
        });
    }
}
