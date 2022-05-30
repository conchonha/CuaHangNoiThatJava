package com.example.myapplication.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Taikhoan;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.ChiTietSanPhamViewModel;
import com.example.myapplication.viewmodel.activity.DangKyViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity {
    private EditText text_UserName, text_PassWord, text_PhoneNumBer, text_Email, text_Adress;
    private Button btndangky;
    private DangKyViewModel viewModel;
    private Taikhoan taikhoan;
    private TextView txtTitleDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        viewModel = ViewModelProviders.of(this).get(DangKyViewModel.class);
        anhxa();
        onClick();
        listener();
    }

    private void listener() {
        viewModel.checkSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        viewModel.checkUpdateSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void onClick() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taikhoan != null){
                    viewModel.updateTaiKhoan( taikhoan.getIdUser().toString(),
                            text_UserName.getText().toString(),
                            text_PassWord.getText().toString(),
                            text_Adress.getText().toString(),
                            text_Email.getText().toString(),
                            text_PhoneNumBer.getText().toString());
                    return;
                }
                viewModel.dangKyTaiKhoan(text_UserName.getText().toString(), text_PassWord.getText().toString(),
                        text_PhoneNumBer.getText().toString(), text_Email.getText().toString(),
                        text_Adress.getText().toString(),getIntent().getIntExtra("AdminId",0)+"");
            }
        });
    }

    private void updateView(Taikhoan taikhoan) {
        text_UserName.setText(taikhoan.getUsername());
        text_PhoneNumBer.setText(taikhoan.getSoDienThoai());
        text_Email.setText(taikhoan.getEmail());
        text_Adress.setText(taikhoan.getDiaChi());
        txtTitleDangKy.setText("Update");
        btndangky.setText("Update");
    }

    private void anhxa() {
        text_UserName = findViewById(R.id.text_UserName);
        text_PassWord = findViewById(R.id.text_PassWord);
        text_PhoneNumBer = findViewById(R.id.text_PhoneNumBer);
        text_Email = findViewById(R.id.text_Email);
        text_Adress = findViewById(R.id.text_Adress);
        btndangky = findViewById(R.id.btndangky);
        txtTitleDangKy = findViewById(R.id.tvMountain);

        taikhoan = (Taikhoan) getIntent().getSerializableExtra("TaiKhoan");
        if(taikhoan != null){
            updateView(taikhoan);
        }
    }
}
