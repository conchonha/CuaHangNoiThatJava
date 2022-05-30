package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Adapter_RecyclerviewSanpham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.DangNhapViewModel;
import com.example.myapplication.viewmodel.activity.TimKiemViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiem extends AppCompatActivity {
    private Toolbar toolbartimkiem;
    private RecyclerView recyclerviewtimkim;
    private TimKiemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        viewModel = ViewModelProviders.of(this).get(TimKiemViewModel.class);

        init();
        Intent intent=getIntent();
        String timkim=intent.getStringExtra("timkim");
        if(!timkim.equals("")){
            viewModel.getdataproductsearch(timkim);
        }
        viewModel.listSanPham.observe(this, new Observer<ArrayList<Sanpham>>() {
            @Override
            public void onChanged(ArrayList<Sanpham> sanphams) {
                addRecyclerview(sanphams);
            }
        });
    }


    private void addRecyclerview(ArrayList<Sanpham>arrayList){
        recyclerviewtimkim=findViewById(R.id.recyclerviewtimkim);
        recyclerviewtimkim.setHasFixedSize(true);
        recyclerviewtimkim.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        Adapter_RecyclerviewSanpham adapter=new Adapter_RecyclerviewSanpham(arrayList,TimKiem.this);
        recyclerviewtimkim.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void init() {

        toolbartimkiem=findViewById(R.id.toolbartimkiem);
        setSupportActionBar(toolbartimkiem);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbartimkiem.setNavigationIcon(R.drawable.back);
        toolbartimkiem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
