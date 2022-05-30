package com.example.myapplication.viewmodel.admin;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

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
public class QuanLySanPhamViewModel extends AndroidViewModel {
    public MutableLiveData<List<Sanpham>> listSanPham = new MutableLiveData<>();

    public QuanLySanPhamViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData(){
        DataService dataService = APIServices.getService();
        Call<List<Sanpham>> calback = dataService.getAllSanPham();

        calback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                listSanPham.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Toast.makeText(
                        getApplication(),
                        t.getMessage().toString(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
