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
import com.example.myapplication.viewmodel.fragment.Fragment4ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Fragment_4 extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Adapter_RecyclerviewSanpham adapter_recyclerviewSanpham;
    private TextView txtxemthemhuawei;
    private Fragment4ViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(Fragment4ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_vanhocdangian,container,false);
        anhxa();
        viewModel.getdatasanpham();
        onclickxemthem();
        listenerData();
        return view;
    }

    private void listenerData() {
        viewModel.listSanPham.observe(getViewLifecycleOwner(), new Observer<List<Sanpham>>() {
            @Override
            public void onChanged(List<Sanpham> sanphams) {
                adapter_recyclerviewSanpham=new Adapter_RecyclerviewSanpham((ArrayList<Sanpham>) sanphams,getContext());
                recyclerView.setAdapter(adapter_recyclerviewSanpham);
                adapter_recyclerviewSanpham.notifyDataSetChanged();
            }
        });
    }

    private void onclickxemthem() {
        txtxemthemhuawei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten=new Intent(getContext(), SanPham.class);
                inten.putExtra("id",4);
                startActivity(inten);
            }
        });
    }

    private void anhxa() {
        txtxemthemhuawei=view.findViewById(R.id.txtxemthemhuawei);
        recyclerView=view.findViewById(R.id.recyclerviewhuawei);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}
