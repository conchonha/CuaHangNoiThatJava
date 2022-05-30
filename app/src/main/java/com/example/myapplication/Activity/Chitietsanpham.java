package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.Banner_Adapter;
import com.example.myapplication.Fragment.Fragment_Danhgiasanpham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.activity.ChiTietSanPhamViewModel;

public class Chitietsanpham extends AppCompatActivity {
    private Toolbar toolbarchitiet;
    private ViewPager viewpagerchitietsanpham;
    private Sanpham sanpham;
    private String[] arrayhinh;
    private TextView txttensanpham1, txtgiasanpham1, txtngaydang1, txttrinhtrang, txtmota1;
    private Button btnthemvaogiohang;
    private int idsp;

    private ChiTietSanPhamViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        viewModel = ViewModelProviders.of(this).get(ChiTietSanPhamViewModel.class);

        anhxa();
        actionbar();
        viewpagerbanner();
        themvaogiohang();
        addFragment(sanpham.getID() + "");
        listener();
    }

    private void listener() {
        viewModel.checkTCallBack.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(getApplicationContext(), GioHang.class));
                }
            }
        });
    }

    private void addFragment(String masp) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Danhgiasanpham fragment = new Fragment_Danhgiasanpham();
        Bundle bundle = new Bundle();
        bundle.putString("masp", masp);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.linner12, fragment);
        fragmentTransaction.commit();
    }

    private void themvaogiohang() {
        btnthemvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap.sharedPreferences.getString("username", "").equals("")) {
                    startActivity(new Intent(getApplicationContext(), DangNhap.class));
                } else {
                    viewModel.themSPGioHang(sanpham.getID() + "", DangNhap.sharedPreferences.getInt("iduser", 0) + "");
                }
            }
        });
    }

    private void anhxa() {
        btnthemvaogiohang = findViewById(R.id.btnthemvaogiohang);
        txttensanpham1 = findViewById(R.id.txttensanpham1);
        txtgiasanpham1 = findViewById(R.id.txtgiasanpham1);
        txttrinhtrang = findViewById(R.id.txttrinhtrang);
        txtmota1 = findViewById(R.id.txtmota1);

    }

    private void viewpagerbanner() {
        Intent intent=getIntent();
        sanpham= (Sanpham) intent.getSerializableExtra("sanpham");
        arrayhinh=sanpham.getMoTaSP().split("@");
        viewpagerchitietsanpham = findViewById(R.id.viewpagerchitietsanpham);

        String array[] = new String[arrayhinh.length];
        array[0] = sanpham.getHinhAnhSP();
        for (int i = 1; i < arrayhinh.length; i++) {
            array[i] = arrayhinh[i];
        }

        Banner_Adapter adapter = new Banner_Adapter(Chitietsanpham.this, array);
        viewpagerchitietsanpham = findViewById(R.id.viewpagerchitietsanpham);
        viewpagerchitietsanpham.setAdapter(adapter);
        txtmota1.setText(arrayhinh[0]);
        txttensanpham1.setText(sanpham.getTenSP());
        txttrinhtrang.setText("Trình Trạng: Còn Hàng");
        txtgiasanpham1.setText(viewModel.getGiaSanPhamNgayKhuyenMai(sanpham));
    }

    private void actionbar() {
        toolbarchitiet = findViewById(R.id.toolbarchitiet);
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarchitiet.setNavigationIcon(R.drawable.back);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
