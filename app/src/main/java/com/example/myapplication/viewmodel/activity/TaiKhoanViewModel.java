package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Activity.DangNhap.editor;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class TaiKhoanViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean>checkSuccess = new MutableLiveData<>();
    public TaiKhoanViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateHinhAnh(String imagetostring, final String toString) {
        DataService dataService= APIServices.getService();
        Call<String> call=dataService.Updatehinhanh(imagetostring,toString);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("AAA","UpdateHinhanh: "+response.toString());
                if(response.isSuccessful()){
                    editor.putString("hinhanh",APIServices.urlhinh+"img/"+toString+".jpg");
                    editor.commit();
                    checkSuccess.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
