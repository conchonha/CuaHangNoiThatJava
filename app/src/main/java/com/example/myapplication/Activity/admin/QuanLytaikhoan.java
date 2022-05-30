package com.example.myapplication.Activity.admin;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.DangKy;
import com.example.myapplication.Adapter.Adapter_Quanlytaikhoan;
import com.example.myapplication.Model.Taikhoan;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.ChiTietSanPhamViewModel;
import com.example.myapplication.viewmodel.admin.QuanLyTaiKhoanViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLytaikhoan extends AppCompatActivity {
    private Toolbar toolbarquanlytaikhoan;
    private RecyclerView recyclerviewquanlytaikhoan;
    private QuanLyTaiKhoanViewModel viewModel;
    private int REQUEST_CODE  = 1000;
    private RadioButton rdoAdmin, rdoKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_lytaikhoan);
        viewModel = ViewModelProviders.of(this).get(QuanLyTaiKhoanViewModel.class);

        initView();
        onClick();
        listener();
    }

    private void listener() {
        viewModel.getdatataikhoan();
        viewModel.listTaiKhoan.observe(this, new Observer<ArrayList<Taikhoan>>() {
            @Override
            public void onChanged(ArrayList<Taikhoan> taikhoans) {
                setrecyclerview(taikhoans);
            }
        });
    }

    private void onClick() {
        toolbarquanlytaikhoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = View.inflate(getApplicationContext(),R.layout.dialog_chon_tai_khoan,null);

                Dialog dialog = new Dialog(QuanLytaikhoan.this);
                dialog.setContentView(view1);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rdoAdmin = view1.findViewById(R.id.rdoAdmin);
                rdoKhachHang = view1.findViewById(R.id.rdoKhachHang);

                dialog.findViewById(R.id.btnGui).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id;
                        if (rdoAdmin.isChecked()) {
                            id = 0;
                        } else if (rdoKhachHang.isChecked()) {
                            id = 1;
                        } else {
                            id = 2;
                        }
                        startActivityForResult(new Intent(QuanLytaikhoan.this, DangKy.class).putExtra("AdminId", id),REQUEST_CODE);
                    }
                });
                dialog.show();
            }
        });
    }

    private void initView() {
        toolbarquanlytaikhoan = findViewById(R.id.toolbarquanlytaikhoan);
        setSupportActionBar(toolbarquanlytaikhoan);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarquanlytaikhoan.setNavigationIcon(R.drawable.back);
    }


    private void setrecyclerview(ArrayList<Taikhoan> arrayList) {
        recyclerviewquanlytaikhoan = findViewById(R.id.recyclerviewquanlytaikhoan);
        recyclerviewquanlytaikhoan.setHasFixedSize(true);
        recyclerviewquanlytaikhoan.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        Adapter_Quanlytaikhoan adpter = new Adapter_Quanlytaikhoan(getApplicationContext(), R.layout.layout_quanlytaikhoan, arrayList,this);
        recyclerviewquanlytaikhoan.setAdapter(adpter);
        adpter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateTaiKhoan(Taikhoan taikhoan){
        startActivityForResult(new Intent(QuanLytaikhoan.this, DangKy.class).putExtra("TaiKhoan", taikhoan),REQUEST_CODE);
    }

    public void refeshPage(){
        finish();
        startActivity(getIntent());
    }
}
