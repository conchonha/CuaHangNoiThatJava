package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.myapplication.Activity.Chitietsanpham;
import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Activity.GioHang;
import com.example.myapplication.Activity.SanPham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class ChiTietSanPhamViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean>checkTCallBack = new MutableLiveData<>();

    public ChiTietSanPhamViewModel(@NonNull Application application) {
        super(application);
    }
    public String getGiaSanPhamNgayKhuyenMai(Sanpham sanpham){
        Calendar calendar=Calendar.getInstance();
        DecimalFormat simpleDateFormat=new DecimalFormat("###,###,###");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String giasaukhuyanmai = "";

        if(sanpham.getGiamGia()>0 && !sanpham.getNgayGiamGia().equals("")){
            Date ngaykhuyenmai= null;
            Date ngayhientai=null;
            try {
                ngaykhuyenmai = format.parse(sanpham.getNgayGiamGia()+"");
                ngayhientai=format.parse(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(ngaykhuyenmai.compareTo(ngayhientai)>0){
                float giagiam=(float) (100-sanpham.getGiamGia())/100;
                float giaspsaukhuyenmai=(float)giagiam*sanpham.getGiaSP();
                giasaukhuyanmai = "Giá"+simpleDateFormat.format((int)giaspsaukhuyenmai)+"Đ";
            }
        }else{
            giasaukhuyanmai = "Giá"+simpleDateFormat.format(sanpham.getGiaSP())+"Đ";
        }
        return giasaukhuyanmai;
    }

    public void themSPGioHang(String s, String iduser) {
        DataService dataService= APIServices.getService();
        Call<String> calback=dataService.themvaogiohang(s,iduser);
        calback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","Themvaogiohang"+response.toString());
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), "Them Thanh cong", Toast.LENGTH_SHORT).show();
                    checkTCallBack.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
