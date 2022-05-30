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
import com.example.myapplication.viewmodel.fragment.Fragment6ViewModel;

import java.util.ArrayList;

public class Fragment6 extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Adapter_RecyclerviewSanpham adapter_recyclerviewSanpham;
    private TextView txtrealme;
    private Fragment6ViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(Fragment6ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_phapluat,container,false);
        anhxa();
        viewModel.getdatasanpham();
        onclickxemthem();
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

    private void onclickxemthem() {
        txtrealme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SanPham.class);
                intent.putExtra("id",6);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        txtrealme=view.findViewById(R.id.txtrealme);
        recyclerView=view.findViewById(R.id.recyclerviewhuawei);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

    }
}
