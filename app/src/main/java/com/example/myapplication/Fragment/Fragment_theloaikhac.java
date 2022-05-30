package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.SanPham;
import com.example.myapplication.Adapter.Adapter_RecyclerviewSanpham;
import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.fragment.TheLoaiKhacViewModel;

import java.util.ArrayList;

public class Fragment_theloaikhac extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    Adapter_RecyclerviewSanpham adapter_recyclerviewSanpham;
    TheLoaiKhacViewModel viewModel;
    private TextView txtxemthemsony;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(TheLoaiKhacViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_theloaikhac,container,false);
        anhxa();
        viewModel.getdatasanpham();
        onclickcxemthem();
        listener();
        return view;
    }

    private void listener() {
        viewModel.listSanPham.observe(getViewLifecycleOwner(), new Observer<ArrayList<Sanpham>>() {
            @Override
            public void onChanged(ArrayList<Sanpham> sanphams) {
                adapter_recyclerviewSanpham=new Adapter_RecyclerviewSanpham(sanphams,getContext());
                recyclerView.setAdapter(adapter_recyclerviewSanpham);
                adapter_recyclerviewSanpham.notifyDataSetChanged();
            }
        });
    }

    private void onclickcxemthem() {
        txtxemthemsony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SanPham.class);
                intent.putExtra("id",8);
                startActivity(intent);
            }
        });
    }



    private void anhxa() {
        txtxemthemsony=view.findViewById(R.id.txtxemthemsony);
        recyclerView=view.findViewById(R.id.recyclerviewhuawei);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

    }
}
