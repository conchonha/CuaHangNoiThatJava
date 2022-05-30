package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.Adapter.Adapter_RecyclerviewSanpham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.DangNhapViewModel;
import com.example.myapplication.viewmodel.activity.SanPhamViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPham extends AppCompatActivity {
    private SanPhamViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        viewModel = ViewModelProviders.of(this).get(SanPhamViewModel.class);

        anhxa();
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",4);
        final RecyclerView recyclerviewsanpham=findViewById(R.id.recyclerviewsanpham);
        recyclerviewsanpham.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerviewsanpham.setHasFixedSize(true);

        viewModel.getSanPhamId(id+"");
        viewModel.listSanPham.observe(this, new Observer<ArrayList<Sanpham>>() {
            @Override
            public void onChanged(ArrayList<Sanpham> sanphams) {
                final Adapter_RecyclerviewSanpham adapter=new Adapter_RecyclerviewSanpham(sanphams, SanPham.this);
                recyclerviewsanpham.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void anhxa() {
        Toolbar toolbarsanpham=findViewById(R.id.toolbarsanpham);
        setSupportActionBar(toolbarsanpham);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarsanpham.setNavigationIcon(R.drawable.back);
        toolbarsanpham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
