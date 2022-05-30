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
import com.example.myapplication.viewmodel.fragment.Fragment7ViewModel;

import java.util.ArrayList;

public class Fragment7 extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    Adapter_RecyclerviewSanpham adapter_recyclerviewSanpham;
    private TextView txtxemthemvivo;
    private Fragment7ViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(Fragment7ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_tpvanhocvietnam,container,false);
        anhxa();
        viewModel.getdatasanpham();
        Onclickxemthem();
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

    private void Onclickxemthem() {
        txtxemthemvivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SanPham.class);
                intent.putExtra("id",7);
                startActivity(intent);
            }
        });
    }


    private void anhxa() {
        txtxemthemvivo=view.findViewById(R.id.txtxemthemvivo);
        recyclerView=view.findViewById(R.id.recyclerviewhuawei);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

    }
}
