package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Activity.DangKy;
import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

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
public class DangKyViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> checkSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkUpdateSuccess = new MutableLiveData<>();

    public DangKyViewModel(@NonNull Application application) {
        super(application);
    }

    public void dangKyTaiKhoan(String text_UserName, String text_PassWord, String text_PhoneNumBer, String text_Email, String text_Adress, String loai) {
        if (text_UserName.equals("") || text_PassWord.equals("") ||
                text_PhoneNumBer.equals("") || text_Email.equals("") ||
                text_Adress.equals("")) {
            Toast.makeText(getApplication(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
        } else if (!text_Email.endsWith("@gmail.com")) {
            Toast.makeText(getApplication(), "Sai email", Toast.LENGTH_SHORT).show();
        } else if (text_PhoneNumBer.length() != 10) {
            Toast.makeText(getApplication(), "Sai sdt", Toast.LENGTH_SHORT).show();
        } else {
            DataService dataService = APIServices.getService();
            Call<String> callback = dataService.dangkytaikhoan(text_UserName,
                    text_PassWord,
                    text_PhoneNumBer,
                    text_Email,
                    text_Adress,
                    loai != null ? loai : 0 + "");
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("AAA", "Create Tai Khoan: " + response.toString());
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                        checkSuccess.postValue(true);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

    public void updateTaiKhoan(String id, String text_UserName, String text_PassWord, String text_Adress, String text_Email, String text_PhoneNumBer) {
        DataService dataService = APIServices.getService();
        Call<String> callback = dataService.Updatetaikhoan(
                id,
                text_UserName,
                text_PassWord,
                text_Adress,
                text_Email,
                text_PhoneNumBer
        );
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                checkUpdateSuccess.postValue(true);
                Log.d("AAA", "Create Tai Khoan: $response");
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Update Tài Khoản Thành Công", Toast.LENGTH_SHORT)
                                    .show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Update Tài Khoản Thất Bại", Toast.LENGTH_SHORT);
            }
        });
    }
}
