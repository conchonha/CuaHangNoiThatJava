package com.example.myapplication.viewmodel.admin;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.admin.ThemSanPham;
import com.example.myapplication.Model.Taikhoan;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.util.ArrayList;
import java.util.Calendar;
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
public class QuanLyTaiKhoanViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Taikhoan>>listTaiKhoan = new MutableLiveData<>();
    public MutableLiveData<Boolean>checkAddSanPham = new MutableLiveData<>();

    public QuanLyTaiKhoanViewModel(@NonNull Application application) {
        super(application);
    }

    public void getdatataikhoan() {
        DataService dataService= APIServices.getService();
        Call<List<Taikhoan>> callback=dataService.getdataalltaikhoan();
        callback.enqueue(new Callback<List<Taikhoan>>() {
            @Override
            public void onResponse(Call<List<Taikhoan>> call, Response<List<Taikhoan>> response) {
                Log.d("AAA","getdata all taikhoan: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<Taikhoan> arrayList= (ArrayList<Taikhoan>) response.body();
                    listTaiKhoan.postValue(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Taikhoan>> call, Throwable t) {

            }
        });
    }

    public void themSanPham(String toString, String toString1, String toString2, String toString3, String urlImg, String toString4, String toString5) {
        DataService dataService = APIServices.getService();
        Call<String> calback = dataService.insertSanPham(
                toString,
                toString1,
                toString2,
                toString3,
                urlImg,
                toString4,
                toString5
        );
        calback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplication(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                checkAddSanPham.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Them that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
