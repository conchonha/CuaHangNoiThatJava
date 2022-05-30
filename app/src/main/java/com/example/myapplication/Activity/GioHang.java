package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.Adapter_Giohang;
import com.example.myapplication.Fragment.Dialog_FragmentThanhtoan;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.DangNhapViewModel;
import com.example.myapplication.viewmodel.activity.GioHangViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHang extends AppCompatActivity {
    private RecyclerView recyclerviewgiohang;
    private Toolbar toolbargiohang;
    private TextView txtgiatong;
    private Button btnthanhtoan;
    private GioHangViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        viewModel = ViewModelProviders.of(this).get(GioHangViewModel.class);

        anhxa();
        viewModel.getdatagiohang();
        btnthanhtoan();
        listener();
    }

    private void listener() {
        viewModel.listGioHang.observe(this, new Observer<ArrayList<com.example.myapplication.Model.GioHang>>() {
            @Override
            public void onChanged(ArrayList<com.example.myapplication.Model.GioHang> gioHangs) {
                Adapter_Giohang adapter=new Adapter_Giohang(gioHangs, com.example.myapplication.Activity.GioHang.this);
                recyclerviewgiohang.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                int tong=0;
                for (int i=0;i<gioHangs.size();i++){
                    tong+=gioHangs.get(i).getGia();
                }
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                txtgiatong.setText(decimalFormat.format(tong)+" Đ");
            }
        });

        viewModel.checkDelete.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
                startActivity(getIntent());
            }
        });

        viewModel.checkTang.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
                startActivity(getIntent());
            }
        });

        viewModel.checkGiam.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void btnthanhtoan() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                Dialog_FragmentThanhtoan dialog_fragmentThanhtoan=new Dialog_FragmentThanhtoan();
                dialog_fragmentThanhtoan.show(fragmentManager,"thanhtoan");
            }
        });
    }

    private void anhxa() {
        btnthanhtoan=findViewById(R.id.btnthanhtoan);
        txtgiatong=findViewById(R.id.txtgiatong);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbargiohang.setNavigationIcon(R.drawable.back);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerviewgiohang=findViewById(R.id.recyclerviewgiohang);
        recyclerviewgiohang.setHasFixedSize(true);
        recyclerviewgiohang.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
    }

    public void Deletegiohang(String id){
        viewModel.Deletegiohang(id);
    }

    public void tang(String id){
        viewModel.tang(id);
    }

    public void giam(String id){
        viewModel.giam(id);
    }

}
