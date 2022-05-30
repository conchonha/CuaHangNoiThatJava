package com.example.myapplication.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Adapter_Donhang;
import com.example.myapplication.Model.DonDatHang;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.activity.DonHangViewModel;

import java.util.ArrayList;

public class DonHang extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbardonhang;

    private DonHangViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        viewModel = ViewModelProviders.of(this).get(DonHangViewModel.class);
        if(DangNhap.sharedPreferences.getString("admin","").equals("")){
            viewModel.getdatadondathang();
        }else{
            getdatadonhangAdmin();
        }

        anhxa();
    }

    private void getdatadonhangAdmin() {
        viewModel.getdatadonhangAdmin();
        viewModel.arrayListDonDatHang.observe(this, new Observer<ArrayList<DonDatHang>>() {
            @Override
            public void onChanged(ArrayList<DonDatHang> donDatHangs) {
                setRecyclerview(donDatHangs);
            }
        });
    }

    private void anhxa() {
        toolbardonhang=findViewById(R.id.toolbardonhang);
        setSupportActionBar(toolbardonhang);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbardonhang.setNavigationIcon(R.drawable.back);
        toolbardonhang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void reload(){
        finish();
        startActivity(getIntent());
    }

    private void setRecyclerview(ArrayList<DonDatHang>arrayList) {
        recyclerView=findViewById(R.id.recyclerviewdonhang);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        Adapter_Donhang adapter_donhang=new Adapter_Donhang(arrayList,DonHang.this,R.layout.layoutdonhang);
        recyclerView.setAdapter(adapter_donhang);
        adapter_donhang.notifyDataSetChanged();
    }
}
