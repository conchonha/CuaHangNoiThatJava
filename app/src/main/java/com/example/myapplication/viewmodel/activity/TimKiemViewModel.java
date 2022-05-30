package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Activity.TimKiem;
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
public class TimKiemViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Sanpham>>listSanPham = new MutableLiveData<>();
    public TimKiemViewModel(@NonNull Application application) {
        super(application);
    }

    public void getdataproductsearch(String timkim) {
        DataService dataService= APIServices.getService();
        Call<List<Sanpham>> call=dataService.getdatasanphamsearch(timkim);
        call.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                Log.d("AAA","getdataproduct search: "+response.toString());
                if(response.isSuccessful()){
                    ArrayList<Sanpham> arrayList= (ArrayList<Sanpham>) response.body();
                    listSanPham.postValue(arrayList);
                    if(arrayList.size()==0){
                        Toast.makeText(getApplication(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplication(), "Không tìm thấy sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {

            }
        });
    }
}
