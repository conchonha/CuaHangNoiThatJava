package com.example.myapplication.Activity.admin.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Sanpham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 5/30/2022
*/
public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.ViewHodler> {
    private List<Sanpham> list = new ArrayList<>();

    public void submit(List<Sanpham> list1) {
        list = list1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodler(View.inflate(parent.getContext(), R.layout.item_sanpham, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView txtGia;
        private TextView txtNgay;
        private ImageView imgHinh;
        private ImageView imgDelete;

        public ViewHodler(@NonNull final View itemView) {
            super(itemView);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataService dataService = APIServices.getService();
                    Log.d("BBB", "onClick: "+list.get(getAdapterPosition()).getID());
                    Call<String>call = dataService.deleteSanPham(list.get(getAdapterPosition()).getID()+"");
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(itemView.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                list.remove(getAdapterPosition());
                                notifyItemChanged(getAdapterPosition());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(itemView.getContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        public void bind(Sanpham sanpham){
            TextView txtName = itemView.findViewById(R.id.txtName);
            txtName.setText(sanpham.getTenSP());
            txtGia.setText("Giá: "+sanpham.getGiaSP());
            txtNgay.setText("Ngày giảm giá: "+sanpham.getNgayGiamGia());
            Picasso.with(itemView.getContext()).load(sanpham.getHinhAnhSP()).into(imgHinh);
        }
    }
}
