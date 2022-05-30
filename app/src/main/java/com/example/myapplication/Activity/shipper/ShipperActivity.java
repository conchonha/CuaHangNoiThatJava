package com.example.myapplication.Activity.shipper;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Model.DonDatHang;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.admin.QuanLyTaiKhoanViewModel;
import com.example.myapplication.viewmodel.shipper.ShipperViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class ShipperActivity extends AppCompatActivity {
    private ShipperViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);
        viewModel = ViewModelProviders.of(this).get(ShipperViewModel.class);

        initView();
        listener();
    }

    private void listener() {
        viewModel.getDataDonHang();
        viewModel.listDonDatHang.observe(this, new Observer<ArrayList<DonDatHang>>() {
            @Override
            public void onChanged(ArrayList<DonDatHang> donDatHangs) {
                Adapter_Donhang_Shipper adapter = new Adapter_Donhang_Shipper(donDatHangs,ShipperActivity.this,R.layout.layoutdonhang);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void reload(){
        finish();
        startActivity(getIntent());
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarGiaoHangVaSacNhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rclDonHang);
    }

}
