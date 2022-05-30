package com.example.myapplication.viewmodel.shipper;

import android.app.Application;
import android.widget.Toast;

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
public class ShipperViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<DonDatHang>>listDonDatHang = new MutableLiveData<>();

    public ShipperViewModel(@NonNull Application application) {
        super(application);
    }

    public void getDataDonHang() {
        DataService api = APIServices.getService();
        Call callback = api.trangThaiDonHangDangVanChuyen();
        callback.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    listDonDatHang.postValue((ArrayList<DonDatHang>) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplication(), ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
