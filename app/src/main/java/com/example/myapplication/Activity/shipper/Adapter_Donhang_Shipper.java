package com.example.myapplication.Activity.shipper;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Activity.DonHang;
import com.example.myapplication.Activity.HoaDon;
import com.example.myapplication.Model.DonDatHang;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIServices;
import com.example.myapplication.Service.DataService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Donhang_Shipper extends RecyclerView.Adapter <Adapter_Donhang_Shipper.Viewhdler>{
    private ArrayList<DonDatHang>arrayList;
    private ShipperActivity context;
    private int layout;
    private View view;

    public Adapter_Donhang_Shipper(ArrayList<DonDatHang> arrayList, ShipperActivity context, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public Viewhdler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=View.inflate(context,layout,null);
        return new Viewhdler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewhdler holder, final int position) {
        DonDatHang donDatHang=arrayList.get(position);
        holder.trinhtrang.setText(donDatHang.getTrinhtrang());
        holder.ngaydat.setText(donDatHang.getNgaydat());
        holder.iddonhang.setText(donDatHang.getId()+"");

        holder.imgmenudonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.imgmenudonhang);
                popupMenu.getMenuInflater().inflate(R.menu.menu_shipper,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.menu_updatedonhang){
                            DataService dataService= APIServices.getService();
                            Log.d("AAAAA", "onMenuItemClick: "+arrayList.get(position).getId()+"");
                            Call<String>call=dataService.updatedonhangHoanThanh(arrayList.get(position).getId()+"");
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("AAA","update donhang: "+response.toString());
                                    if(response.isSuccessful()){
                                        Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                                        context.reload();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("AAA","update donhang: "+t.getMessage().toString());
                                }
                            });
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewhdler extends RecyclerView.ViewHolder{
        private TextView iddonhang,ngaydat,trinhtrang;
        private ImageView imgmenudonhang;
        public Viewhdler(@NonNull View itemView) {
            super(itemView);
            iddonhang=itemView.findViewById(R.id.iddonhang);
            ngaydat=itemView.findViewById(R.id.ngaydat);
            trinhtrang=itemView.findViewById(R.id.trinhtrang);
            imgmenudonhang=itemView.findViewById(R.id.imgmenudonhang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context.getApplicationContext(), HoaDon.class);
                    intent.putExtra("iddonhang",arrayList.get(getPosition()).getId()+"");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
