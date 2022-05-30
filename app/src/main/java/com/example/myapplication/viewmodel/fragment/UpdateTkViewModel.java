package com.example.myapplication.viewmodel.fragment;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class UpdateTkViewModel extends AndroidViewModel {


    public UpdateTkViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateTaiKhoan(String s, final String edtussername, final String edtpass, final String edtdiachi, final String edtemail, final String edtsdt, final FragmentManager fragmentManager) {
        DataService dataService= APIServices.getService();
        Call<String> call=dataService.Updatetaikhoan(s,edtussername,edtpass, edtdiachi,edtemail,edtsdt);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("AAA","Update taikhoan: "+response.toString());
                if(response.isSuccessful()){
                    Log.d("AAA",response.body());
                    Toast.makeText(getApplication(), "Update Thanh cong", Toast.LENGTH_SHORT).show();
                    DangNhap.editor.putString("username", edtussername);
                    DangNhap.editor.putString("password", edtpass);
                    DangNhap.editor.putString("email", edtemail);
                    DangNhap.editor.putString("sodienthoai",edtsdt);
                    DangNhap.editor.putString("diachi",edtdiachi);
                    DangNhap.editor.commit();
                    Fragment fragment= fragmentManager.findFragmentByTag("updatethongtintk");
                    if(fragment!=null){
                        DialogFragment dialogFragmen= (DialogFragment) fragment;
                        dialogFragmen.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
