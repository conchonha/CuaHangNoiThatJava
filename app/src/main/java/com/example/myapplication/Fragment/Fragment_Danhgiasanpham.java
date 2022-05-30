package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.Chitietsanpham;
import com.example.myapplication.Activity.DangNhap;
import com.example.myapplication.Adapter.Comment_Adapter;
import com.example.myapplication.Model.Danhgia;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.fragment.DanhGiaSanPhamViewModel;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class Fragment_Danhgiasanpham extends Fragment {
    private View view;
    private Chitietsanpham chitietsanpham;
    private RatingBar ratingdanhgiacuabanla;
    private EditText edtcommentt;
    private Button btnguicomment;
    private DanhGiaSanPhamViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DanhGiaSanPhamViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danhgiasanpham, container, false);
        danhgiasanpham();
        Bundle bundle = getArguments();
        if (bundle != null) {
            final String msp = bundle.getString("masp");
            if (!msp.equals("0")) {
                viewModel.getdatadanhgia(msp);
                btnguicomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        danhgiacuaban(msp);
                    }
                });
            }
        }
        listener();
        return view;
    }

    private void listener() {
        viewModel.toast.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("Faid")) {
                    Toast.makeText(getActivity(), "Không Thành công - bạn đã đánh giá rùi", Toast.LENGTH_SHORT).show();
                }
                if (s.equals("sussces")) {
                    Toast.makeText(getActivity(), "Đánh Giá Của Bạn Đã Được Gửi Đi", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }
        });

        viewModel.listDanhGia.observe(getViewLifecycleOwner(), new Observer<ArrayList<Danhgia>>() {
            @Override
            public void onChanged(ArrayList<Danhgia> danhgias) {
                ListView listView = view.findViewById(R.id.listviewcomment);
                Comment_Adapter comment_adapter = new Comment_Adapter(getActivity(), R.layout.layout_dongcomment, danhgias);
                comment_adapter.notifyDataSetChanged();
                listView.setAdapter(comment_adapter);
                setListViewHeightBasedOnChildren(listView);
            }
        });
    }

    private void danhgiacuaban(final String msp) {
        if (DangNhap.sharedPreferences.getString("username", "").equals("") &&
                DangNhap.sharedPreferences.getInt("iduser", 0) == 0) {
            Intent intent = new Intent(getActivity(), DangNhap.class);
            intent.putExtra("phandanhgia", msp);
            startActivity(intent);
            getActivity().finish();
        } else {
            String commentt = "ok";
            commentt = edtcommentt.getText().toString();
            viewModel.postValue(ratingdanhgiacuabanla.getRating(), msp, commentt);
        }
    }


    private void danhgiasanpham() {
        btnguicomment = view.findViewById(R.id.btnguicomment);
        ratingdanhgiacuabanla = view.findViewById(R.id.ratingdanhgiacuabanla);
        edtcommentt = view.findViewById(R.id.edtcommentt);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
