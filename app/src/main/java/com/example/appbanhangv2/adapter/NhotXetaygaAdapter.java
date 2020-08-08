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

public class NhotXetaygaAdapter extends RecyclerView.Adapter<NhotXetaygaAdapter.NhotxetaygaViewHolder> {
    Context context;
    ArrayList<Sanpham> arrayListNhotXetaga;

    public NhotXetaygaAdapter(Context context, ArrayList<Sanpham> arrayListNhotXetaga) {
        this.context = context;
        this.arrayListNhotXetaga = arrayListNhotXetaga;
    }

    @NonNull
    @Override
    public NhotxetaygaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhotxe_tayga,null);
        NhotxetaygaViewHolder nhotxetaygaViewHolder = new NhotxetaygaViewHolder(v);
        return nhotxetaygaViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NhotxetaygaViewHolder holder, int position) {
        Sanpham sanpham = arrayListNhotXetaga.get(position);
        holder.txtTenNhotxetayga.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaNhotxetayga.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"VND");
        holder.txtMotaNhotxeStayga.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imageViewNhotxetayga);
    }

    @Override
    public int getItemCount() {
        return arrayListNhotXetaga.size();
    }

    public class NhotxetaygaViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewNhotxetayga;
        public TextView txtTenNhotxetayga, txtGiaNhotxetayga, txtMotaNhotxeStayga;
        public NhotxetaygaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenNhotxetayga = itemView.findViewById(R.id.tvTenNhotxetayga);
            txtGiaNhotxetayga = itemView.findViewById(R.id.tvGiaNhotxetayga);
            imageViewNhotxetayga = itemView.findViewById(R.id.imgNhotxetayga);
            txtMotaNhotxeStayga = itemView.findViewById(R.id.tvMotaNhotxetayga);
        }
    }
}
