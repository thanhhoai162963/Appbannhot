package com.example.appbanhangv2.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appbanhangv2.R;
import com.example.appbanhangv2.adapter.GiohangAdapter;
import com.example.appbanhangv2.interfaces.OnListenUpdateTotal;
import com.example.appbanhangv2.view.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/*import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;*/

public class Giohangsp extends AppCompatActivity {
    RecyclerView mRcvGiohang;
    GiohangAdapter mGiohangAdapter;
    public  TextView mTongtien;
    Button mThanhtoan,mTieptucmuahang;
    long giaCapnhat ;
    long tongTien =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohangsp);
        Mapping();
        Firework();
        TotalProduct();
        Intent intent1 = getIntent();
        giaCapnhat = intent1.getLongExtra("giaCapnhat",-1);
        Log.d("bbb",giaCapnhat+"");
        OnClick();

    }
    public void Firework(){
        /*final KonfettiView mKonfettiiVew;
        mKonfettiiVew =findViewById(R.id.konfettiView);
        mKonfettiiVew.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, mKonfettiiVew.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);*/
    }
    public void Mapping(){
        mTongtien = findViewById(R.id.tvTongtienspGiohang);
        mThanhtoan = findViewById(R.id.TvThanhtoan);
        mTieptucmuahang = findViewById(R.id.tvTieptucmuahang);
        mRcvGiohang = findViewById(R.id.recyclerviewGiohang);
        mGiohangAdapter = new GiohangAdapter(this, MainActivity.arrayListGiohang);
        mRcvGiohang.setAdapter(mGiohangAdapter);
        mRcvGiohang.setHasFixedSize(true);
        mRcvGiohang.setItemViewCacheSize(20);
        mRcvGiohang.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    public void TotalProduct(){
        tongTien = 0;
        for (int i = 0; i < MainActivity.arrayListGiohang.size() ; i++) {
            tongTien += MainActivity.arrayListGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mTongtien.setText(decimalFormat.format(tongTien)+"VNĐ");
    }
    public void OnClick(){
        mTieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });
        mThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), com.example.appbanhangv2.view.ThongtinKH.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
            }
        });
        mGiohangAdapter.setOnListenerUpdateProduct(new OnListenUpdateTotal() {
            @Override
            public void updateTotalProduct() {
                TotalProduct();
            }
        });
    }
}