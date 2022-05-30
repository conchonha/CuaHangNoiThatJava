package com.example.myapplication.Activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Activity.admin.adapter.AdapterSanPham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.admin.QuanLySanPhamViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class QuanLySanPham extends AppCompatActivity {
    private QuanLySanPhamViewModel viewModel;
    private RecyclerView recyclerView;
    private AdapterSanPham adapterSanPham;
    private int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);
        viewModel = ViewModelProviders.of(this).get(QuanLySanPhamViewModel.class);

        initView();
        onClickView();
        listener();
    }

    private void listener() {
        viewModel.listSanPham.observe(this, new Observer<List<Sanpham>>() {
            @Override
            public void onChanged(List<Sanpham> sanphams) {

                adapterSanPham.submit(sanphams);
            }
        });
    }

    private void onClickView() {
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), ThemSanPham.class), REQUEST_CODE);
            }
        });
    }

    private void initView() {
        adapterSanPham = new AdapterSanPham();
        recyclerView = findViewById(R.id.recyclerviewsanpham);
        recyclerView.setAdapter(adapterSanPham);
        viewModel.loadData();

        Toolbar toolbar = findViewById(R.id.toolbarsanpham);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
