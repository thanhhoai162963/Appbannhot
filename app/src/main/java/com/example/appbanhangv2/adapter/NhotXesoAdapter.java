package com.example.appbanhangv2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appbanhangv2.R;
import com.example.appbanhangv2.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NhotXesoAdapter extends RecyclerView.Adapter<NhotXesoAdapter.NhotXesoViewHolder> {
    Context context;
    ArrayList<Sanpham> arrayListNhotXeSo;

    public NhotXesoAdapter(Context context, ArrayList<Sanpham> arrayListNhotXeSo) {
        this.context = context;
        this.arrayListNhotXeSo = arrayListNhotXeSo;
    }

    @NonNull
    @Override
    public NhotXesoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhot_xeso,null);
         NhotXesoViewHolder nhotXesoViewHolder = new NhotXesoViewHolder(v);
        return nhotXesoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NhotXesoViewHolder holder, int position) {
        Sanpham sanpham = arrayListNhotXeSo.get(position);
        holder.txtTenNhotxeSo.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaNhotxeSo.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"VND");
        holder.txtMotaNhotxeSo.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imageViewNhotxeSo);
    }

    @Override
    public int getItemCount() {
        return arrayListNhotXeSo.size();
    }

    public class NhotXesoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewNhotxeSo;
        public TextView txtTenNhotxeSo, txtGiaNhotxeSo, txtMotaNhotxeSo;
        public NhotXesoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenNhotxeSo = itemView.findViewById(R.id.tvTenNhotxeso);
            txtGiaNhotxeSo = itemView.findViewById(R.id.tvGiaNhotxeso);
            imageViewNhotxeSo = itemView.findViewById(R.id.imgNhotxeso);
            txtMotaNhotxeSo = itemView.findViewById(R.id.tvMotaNhotxeso);

        }
    }
}
