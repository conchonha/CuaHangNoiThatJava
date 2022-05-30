package com.example.myapplication.viewmodel.activity;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Activity.admin.Admin;
import com.example.myapplication.Model.Taikhoan;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
public class DangNhapViewModel extends AndroidViewModel {
    public MutableLiveData<Pair<Integer,Boolean>>typeUser = new MutableLiveData<>();

    public DangNhapViewModel(@NonNull Application application) {
        super(application);
    }

    public void dangNhap(final String edtpassword, final String edttendangnhap, final DangNhap dangNhap) {
        if(edtpassword.equals("") || edttendangnhap.equals("")){
            Toast.makeText(getApplication(), "Không để trống dữ liệu", Toast.LENGTH_SHORT).show();
        }else{
            DataService dataService= APIServices.getService();
            Call<List<Taikhoan>> callback=dataService.dangnhap(edttendangnhap,edtpassword);
            callback.enqueue(new Callback<List<Taikhoan>>() {
                @Override
                public void onResponse(Call<List<Taikhoan>> call, Response<List<Taikhoan>> response) {
                    Log.d("AAA","Dangnhap: "+response.toString());
                    if(response.isSuccessful()){
                        final ArrayList<Taikhoan> arrayList= (ArrayList<Taikhoan>) response.body();
                        if(arrayList.size()>0){
                            if(arrayList.get(0).getLoai()==1){
                                AlertDialog.Builder dialog=new AlertDialog.Builder(dangNhap);
                                dialog.setTitle("Bạn có muốn đăng nhập với quyền admin ?");
                                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.remove("admin");
                                        editor.putString("khachhang","khachhang");
                                        editor.putString("username", edttendangnhap);
                                        editor.putString("password", edtpassword);
                                        editor.putInt("iduser", arrayList.get(0).getIdUser());
                                        editor.putString("email", arrayList.get(0).getEmail());
                                        editor.putString("sodienthoai", arrayList.get(0).getSoDienThoai());
                                        editor.putString("diachi",arrayList.get(0).getDiaChi());
                                        if(arrayList.get(0).getHinhanh().endsWith("jpg")){
                                            editor.putString("hinhanh",APIServices.urlhinh+arrayList.get(0).getHinhanh());
                                        }else{
                                            editor.putString("hinhanh",arrayList.get(0).getHinhanh());
                                        }
                                        editor.commit();
                                        Toast.makeText(getApplication(), "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
                                        typeUser.postValue(new Pair(1,false));
                                    }
                                });
                                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("admin","admin");
                                        editor.remove("khachhang");
                                        editor.commit();
                                        typeUser.postValue(new Pair(1,true));
                                    }
                                });
                                dialog.show();
                            }else if(arrayList.get(0).getLoai()==2){
                                typeUser.postValue(new Pair(2,false));
                            }else{
                                editor.remove("admin");
                                editor.putString("khachhang", "khachhang");
                                editor.putString("username", edttendangnhap);
                                editor.putString("password", edtpassword);
                                editor.putInt("iduser", arrayList.get(0).getIdUser());
                                editor.putString("email", arrayList.get(0).getEmail());
                                editor.putString("sodienthoai", arrayList.get(0).getSoDienThoai());
                                editor.putString("diachi", arrayList.get(0).getDiaChi());
                                if (arrayList.get(0).getHinhanh().endsWith("jpg")) {
                                    editor.putString("hinhanh", APIServices.urlhinh + arrayList.get(0).getHinhanh());
                                } else {
                                    editor.putString("hinhanh", arrayList.get(0).getHinhanh());
                                }
                                editor.commit();
                                Toast.makeText(getApplication(), "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
                                typeUser.postValue(new Pair(0,false));
                            }
                        }else{
                            Toast.makeText(getApplication(), "Tài khoản không chính sác", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Taikhoan>> call, Throwable t) {

                }
            });
        }
    }
}
