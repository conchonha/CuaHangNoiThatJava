package com.example.myapplication.viewmodel.fragment;

import android.app.Application;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Adapter.Comment_Adapter;
import com.example.myapplication.Model.Danhgia;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.text.SimpleDateFormat;
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
public class DanhGiaSanPhamViewModel extends AndroidViewModel {
    public MutableLiveData<String>toast = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Danhgia>>listDanhGia = new MutableLiveData<>();

    public DanhGiaSanPhamViewModel(@NonNull Application application) {
        super(application);
    }

    public void postValue(Float rating,String masp, String comment){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar=Calendar.getInstance();
        String ngay=simpleDateFormat.format(calendar.getTime());
        // Toast.makeText(getActivity(), MainActivity.sharedPreferences.getString("username",comment), Toast.LENGTH_SHORT).show();
        int namsao=0;int bonsao=0;int basao=0;int haisao=0;int motsao=0;
        if(rating>0){
            if (rating == 5) {
                namsao = 1;
            } else if (rating == 4) {
                bonsao = 1;
            } else if (rating == 3) {
                basao = 1;
            } else if (rating == 2) {
                haisao = 1;
            } else if (rating == 1) {
                motsao = 1;
            }
            String username= DangNhap.sharedPreferences.getString("username","");
            postbai(username,masp,ngay,namsao,bonsao,basao,haisao,motsao,comment);
        }
    }

    private void postbai(String username, String msp, String ngay, int namsao, int bonsao, int basao, int haisao, int motsao, String comment) {
        DataService dataService= APIServices.getService();
        Call<String> callback=dataService.postDanhgia(DangNhap.sharedPreferences.getString("username",""),
                msp,ngay,namsao,bonsao,basao,haisao,motsao,comment);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","Danhgiacuaban"+response.toString());
                if(response.isSuccessful()){
                    toast.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void getdatadanhgia(final String msp) {
        if (!msp.equals("")) {
            DataService dataService = APIServices.getService();
            Call<List<Danhgia>> callback = dataService.getdataDanhgia(msp + "");
            callback.enqueue(new Callback<List<Danhgia>>() {
                @Override
                public void onResponse(Call<List<Danhgia>> call, Response<List<Danhgia>> response) {
                    Log.d("AAA", "getdataDanhgia" + response.toString());
                    if (response != null) {
//                        int namsao = 0;
//                        int bonsao = 0;
//                        int basao = 0;
//                        int haisao = 0;
//                        int motsao = 0;
                        listDanhGia.postValue((ArrayList<Danhgia>) response.body());
//                        arrayDanhgia = (ArrayList<Danhgia>) response.body();
//                        if (arrayDanhgia != null) {
//                            for (int i = 0; i < arrayDanhgia.size(); i++) {
//                                namsao += arrayDanhgia.get(i).get5sao();
//                                bonsao += arrayDanhgia.get(i).get4sao();
//                                basao += arrayDanhgia.get(i).get3sao();
//                                haisao += arrayDanhgia.get(i).get2sao();
//                                motsao += arrayDanhgia.get(i).get1sao();
//                            }
//
//                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Danhgia>> call, Throwable t) {
                    Log.d("AAA", "Ero getdata danhgia san pham" + t.toString());
                }
            });
        }
    }
}
