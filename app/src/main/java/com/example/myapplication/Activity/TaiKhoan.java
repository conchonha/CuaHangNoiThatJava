package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Fragment.DialogfragmentUpdatetk;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.example.myapplication.viewmodel.activity.DangNhapViewModel;
import com.example.myapplication.viewmodel.activity.TaiKhoanViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Activity.DangNhap.editor;

public class TaiKhoan extends AppCompatActivity {
    private Button btnluu,btnupdatethongtin;
    private ImageView imgthumuc,imghinh;
    private Toolbar toolbar23;
    private TextView txtuser,txtpassword,txtsodienthoai,email,txtdiachi;
    private int Request_code_image=123;
    private TaiKhoanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TaiKhoanViewModel.class);
        setContentView(R.layout.activity_tai_khoan);
        anhxa();
        init();
        updatehinh();
        updatethongtin();
    }

    private void updatethongtin() {
        btnupdatethongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                DialogfragmentUpdatetk dialog=new DialogfragmentUpdatetk();
                dialog.show(fragmentManager,"updatethongtintk");
            }
        });
    }

    private void updatehinh() {
        imgthumuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(intent.ACTION_PICK);
                intent.setType("image/*");
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent,Request_code_image);
                }
                btnluu.setVisibility(View.VISIBLE);
            }
        });
        viewModel.checkSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Request_code_image && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imghinh.setImageBitmap(bitmap);

                viewModel.updateHinhAnh(imagetostring(bitmap),txtuser.getText().toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imagetostring(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[]hinhanh=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(hinhanh,Base64.DEFAULT);
    }

    private void init() {
        setSupportActionBar(toolbar23);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar23.setNavigationIcon(R.drawable.back);
        toolbar23.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtuser.setText(DangNhap.sharedPreferences.getString("username",""));
        txtpassword.setText(DangNhap.sharedPreferences.getString("password",""));
        txtsodienthoai.setText(DangNhap.sharedPreferences.getString("sodienthoai",""));
        email.setText(DangNhap.sharedPreferences.getString("email",""));
        txtdiachi.setText(DangNhap.sharedPreferences.getString("diachi",""));
        Picasso.with(getApplicationContext()).load(DangNhap.sharedPreferences.getString("hinhanh","")).into(imghinh);
    }

    private void anhxa() {
        btnupdatethongtin=findViewById(R.id.btnupdatethongtin);
         btnluu=findViewById(R.id.btnluu);
        imgthumuc=findViewById(R.id.imgthumuc);
        imghinh=findViewById(R.id.immm);
        toolbar23= findViewById(R.id.toolbar23);
        txtuser=findViewById(R.id.user);
        txtpassword= findViewById(R.id.pass);
        txtsodienthoai= findViewById(R.id.sdt);
        email=findViewById(R.id.idd);
        txtdiachi= findViewById(R.id.diachi);
    }
}
