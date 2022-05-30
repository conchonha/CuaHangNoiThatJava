package com.example.myapplication.Activity.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.admin.QuanLyTaiKhoanViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class ThemSanPham extends AppCompatActivity {
    private QuanLyTaiKhoanViewModel viewModel;
    private final int REQUEST_CODE_IMAGEVIEW = 1000;
    private String urlImg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        viewModel = ViewModelProviders.of(this).get(QuanLyTaiKhoanViewModel.class);

        initView();
        onClick();
        listener();
    }

    private void listener() {
        viewModel.checkAddSanPham.observe(this, new Observer<Boolean>() {
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
        findViewById(R.id.imgInsert).setOnClickListener((new View.OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, ThemSanPham.this.REQUEST_CODE_IMAGEVIEW);
            }
        }));

        //insert san pham
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText TenSP = findViewById(R.id.TenSP);
                EditText GiaSP = findViewById(R.id.GiaSP);
                EditText MoTaSP = findViewById(R.id.MoTaSP);
                EditText IDSP = findViewById(R.id.IDSP);
                TextView NgayGiamGia = findViewById(R.id.ngayGiamGia);
                EditText GiamGia = findViewById(R.id.GiamGia);

                if (TenSP.getText().toString().equals("")) {
                    Toast.makeText(ThemSanPham.this, "Vui lòng điền đúng formart", Toast.LENGTH_LONG).show();
                    return;
                }

                viewModel.themSanPham(TenSP.getText().toString(),
                        GiaSP.getText().toString(),
                        NgayGiamGia.getText().toString(),
                        GiamGia.getText().toString(),
                        urlImg,
                        MoTaSP.getText().toString(),
                        IDSP.getText().toString());
            }
        });
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toobarAddSanPham);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int nam = calendar.get(Calendar.YEAR);
        final int thang = calendar.get(Calendar.MONTH);
        final int ngay = calendar.get(Calendar.DATE);

        final TextView ngaygiamgia = findViewById(R.id.ngayGiamGia);
        ngaygiamgia.setText(ngay + "/" + (thang - 1) + "/" + nam);
        ngaygiamgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(ThemSanPham.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                ngaygiamgia.setText(i2 + "/" + i1 + "/" + i);
                            }
                        }, nam, thang, ngay);
                dpd.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGEVIEW && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ImageView img = findViewById(R.id.imgInsert);
                img.setImageBitmap(bitmap);
                urlImg = toArrayString(bitmap);
                Log.d("SangTB", "onActivityResult: $urlImg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String toArrayString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }
}
