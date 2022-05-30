package com.example.myapplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.admin.Admin;
import com.example.myapplication.Activity.shipper.ShipperActivity;
import com.example.myapplication.Model.Taikhoan;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.ChiTietSanPhamViewModel;
import com.example.myapplication.viewmodel.activity.DangNhapViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {
    private EditText edttendangnhap, edtpassword;
    private Button btndangnhap;
    private ImageView cloud1, star;
    private TextView txtdangky;
    Animation animCloud, animStar;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private DangNhapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        viewModel = ViewModelProviders.of(this).get(DangNhapViewModel.class);

        anhxxa();
        if (!sharedPreferences.getString("khachhang", "").equals("")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        if (!sharedPreferences.getString("admin", "").equals("")) {
            startActivity(new Intent(getApplicationContext(), Admin.class));
        }

        listener();
    }

    private void listener() {
        viewModel.typeUser.observe(this, new Observer<Pair<Integer, Boolean>>() {
            @Override
            public void onChanged(Pair<Integer, Boolean> integerBooleanPair) {
                if(integerBooleanPair.first == 1){
                    if(integerBooleanPair.second){
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                        return;
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else if(integerBooleanPair.first == 2){
                    startActivity(new Intent(getApplicationContext(), ShipperActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void anhxxa() {
        txtdangky = findViewById(R.id.txtdangky);
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DangKy.class));
            }
        });
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        cloud1 = findViewById(R.id.cloud1);
        star = findViewById(R.id.star);
        animCloud = AnimationUtils.loadAnimation(this, R.anim.animcloud);
        animStar = AnimationUtils.loadAnimation(this, R.anim.animstar);
        cloud1.startAnimation(animCloud);
        star.startAnimation(animStar);
        edttendangnhap = findViewById(R.id.edttendangnhap);
        edtpassword = findViewById(R.id.edtpassword);
        btndangnhap = findViewById(R.id.btndangnhap);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.dangNhap(edtpassword.getText().toString(), edttendangnhap.getText().toString(),DangNhap.this);
            }
        });
    }
}
