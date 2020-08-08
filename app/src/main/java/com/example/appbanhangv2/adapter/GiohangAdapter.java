package com.example.appbanhangv2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangv2.interfaces.OnListenUpdateTotal;
import com.example.appbanhangv2.model.Giohang;
import com.example.appbanhangv2.R;
import com.example.appbanhangv2.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.GioHangViewHolder>   {
    Context context;
    ArrayList<Giohang> arrayListGiohang;
    long giaMoi , giaMoisp;
    OnListenUpdateTotal mOnListenUpdateTotal;
    public GiohangAdapter(Context context, ArrayList<Giohang> arrayListGiohang) {
        this.context = context;
        this.arrayListGiohang = arrayListGiohang;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,null);
        GioHangViewHolder gioHangViewHolder = new GioHangViewHolder(v);
        return gioHangViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        Giohang giohang = arrayListGiohang.get(position);
        holder.txtTenSpGiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSpGiohang.setText("Giá:"+decimalFormat.format(giohang.getGiasp())+"VND");
        holder.txtSoluong.setText(giohang.getSoluongsp()+"");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imgGiohang);
    }

    @Override
    public int getItemCount() {
        return arrayListGiohang.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder{
        Button btnThem, btnXoa;
        TextView txtTenSpGiohang, txtGiaSpGiohang, txtSoluong;
        ImageView imgGiohang;
        int soluongSp,soluongChitietSp;
        long giaKhiThemGiohang,giaMoiSp1,giaThucte;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            btnThem = itemView.findViewById(R.id.btnThem);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            txtTenSpGiohang = itemView.findViewById(R.id.tvTenGiohang);
            txtGiaSpGiohang = itemView.findViewById(R.id.tvGiaGiohang);
            txtSoluong = itemView.findViewById(R.id.tvSoluong);
            imgGiohang =itemView.findViewById(R.id.imgGiohang);
            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public  void onClick(View v) {
                    btnXoa.setVisibility(View.VISIBLE);
                    soluongChitietSp = MainActivity.arrayListGiohang.get(getPosition()).getSoluongsp();
                    soluongSp = Integer.parseInt(txtSoluong.getText().toString());
                    soluongSp++;
                    giaMoi = MainActivity.arrayListGiohang.get(getPosition()).getGiasp();
                    giaMoisp = giaMoi / soluongChitietSp;
                    giaMoi = (giaMoi / soluongChitietSp) * soluongSp;
                    MainActivity.arrayListGiohang.get(getPosition()).setGiasp(giaMoi);
                    MainActivity.arrayListGiohang.get(getPosition()).setSoluongsp(soluongSp);
                    txtSoluong.setText(soluongSp +"");
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    txtGiaSpGiohang.setText("Giá: "+decimalFormat.format(giaMoi)+" VNĐ");
                    if(soluongSp >= 10){
                        btnThem.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "hết hàng ban vui lòng chọn sp khac nhé!", Toast.LENGTH_LONG).show();
                    }
                    mOnListenUpdateTotal.updateTotalProduct();
                }
            });
            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnThem.setVisibility(View.VISIBLE);
                    soluongChitietSp = MainActivity.arrayListGiohang.get(getPosition()).getSoluongsp();
                    soluongSp = Integer.parseInt(txtSoluong.getText().toString());
                    soluongSp--;
                    giaMoi = MainActivity.arrayListGiohang.get(getPosition()).getGiasp();
                    giaMoisp = giaMoi / soluongChitietSp;
                    giaMoi = (giaMoi / soluongChitietSp) * soluongSp;
                    MainActivity.arrayListGiohang.get(getPosition()).setGiasp(giaMoi);
                    MainActivity.arrayListGiohang.get(getPosition()).setSoluongsp(soluongSp);
                    txtSoluong.setText(soluongSp +"");
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    txtGiaSpGiohang.setText("Giá: "+decimalFormat.format(giaMoi)+" VNĐ");
                    if(soluongSp <= 0){
                        btnXoa.setVisibility(View.INVISIBLE);
                        arrayListGiohang.remove(getLayoutPosition());
                        notifyItemRemoved(getLayoutPosition());
                    }
                    mOnListenUpdateTotal.updateTotalProduct();
                }
            });
        }
    }
    public void setOnListenerUpdateProduct(OnListenUpdateTotal mOnListenUpdateTotal){
        this.mOnListenUpdateTotal = mOnListenUpdateTotal;
    }
}
