package com.example.appbanhangv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhangv2.R;
import com.example.appbanhangv2.model.Giohang;
import com.example.appbanhangv2.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Chitietsanpham extends AppCompatActivity {
    TextView mtxtTenSp, mtxtGiasp, mtxtMotasp;
    ImageView mImgsp;
    Button mbtnDatHang;
    Spinner mSpinner;
    Integer Giasp,ID;
    String Tenchitietsp;
    String strImage;
    long GiaMoi;
    int soluong, soluongthucte, phaobong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Mapping();
        CountSpinner();
        ClickButton();
    }

    @Override
    protected void onStart() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        ID = sanpham.getID();
        Tenchitietsp = sanpham.getTensanpham();
        Giasp = sanpham.getGiasanpham();
        String motasp = sanpham.getMotasanpham();
        strImage = sanpham.getHinhsanpham();
        mtxtTenSp.setText(Tenchitietsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtxtGiasp.setText(decimalFormat.format(Giasp)+"VND");
        mtxtMotasp.setText(motasp);
        Picasso.with(this).load(strImage).into(mImgsp);
        /* ID = intent1.getIntExtra("idnhotxeso",-1);
        Tenchitietsp = intent1.getStringExtra("tennhotxeso");
        Giasp = intent1.getIntExtra("gianhotxeso",-1);
        String Motasp = intent1.getStringExtra("motanhotxeso");
        strImage= String.valueOf(intent1.getStringExtra("hinhnhotxeso"));
        Picasso.with(this).load(strImage).into(mImgsp);
        mtxtTenSp.setText(Tenchitietsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtxtGiasp.setText(decimalFormat.format(Giasp)+"VND");
        mtxtMotasp.setText(Motasp);*/
        super.onStart();
    }

    private void Mapping(){
        mtxtTenSp = findViewById(R.id.tvTenChitietsp);
        mtxtGiasp = findViewById(R.id.tvGiaChitietsp);
        mtxtMotasp = findViewById(R.id.tvMotaChitietsp);
        mImgsp = findViewById(R.id.imgChitietSp);
        mbtnDatHang = findViewById(R.id.btnChitietSp);
        mSpinner = findViewById(R.id.spinner);
    }
    public void ClickButton(){

        mbtnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.arrayListGiohang.size() > 0){
                    int sl = Integer.parseInt(mSpinner.getSelectedItem().toString());
                    boolean exist =false;
                    for (int i = 0; i < MainActivity.arrayListGiohang.size() ; i++) {
                        if(MainActivity.arrayListGiohang.get(i).getIdsp() == ID) {
                            MainActivity.arrayListGiohang.get(i).setSoluongsp(MainActivity.arrayListGiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.arrayListGiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.arrayListGiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.arrayListGiohang.get(i).setGiasp(Giasp+ MainActivity.arrayListGiohang.get(i).getSoluongsp());
                            exist =true;
                        }

                    }
                    if(exist == false){
                        soluong = Integer.parseInt(mSpinner.getSelectedItem().toString());
                        GiaMoi = soluong * Giasp;
                        MainActivity.arrayListGiohang.add(new Giohang(ID,Tenchitietsp,GiaMoi,strImage,soluong));
                    }else {
                        for (int i = 0; i < MainActivity.arrayListGiohang.size() ; i++) {
                            if( ID == MainActivity.arrayListGiohang.get(i).getIdsp()){
                                soluong = Integer.parseInt(mSpinner.getSelectedItem().toString());
                                soluongthucte = soluong;
                                GiaMoi = soluong * Giasp;
                                GiaMoi = GiaMoi + (soluong*Giasp);
                                soluong = soluong + soluongthucte;
                                MainActivity.arrayListGiohang.get(i).setSoluongsp(soluong);
                                MainActivity.arrayListGiohang.get(i).setGiasp(GiaMoi);
                            }
                        }
                    }
                }else {
                    int soluong = Integer.parseInt(mSpinner.getSelectedItem().toString());
                    long GiaMoi = soluong * Giasp;
                    MainActivity.arrayListGiohang.add(new Giohang(ID,Tenchitietsp,GiaMoi,strImage,soluong));
                }
                Intent intent1 = new Intent(getApplicationContext(), Giohangsp.class);
                startActivity(intent1);
                finish();
            }
        });

    }
    public void CountSpinner(){
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>
                (this,R.layout.support_simple_spinner_dropdown_item,soluong);
        mSpinner.setAdapter(arrayAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGiohang:
                Intent intent1 = new Intent(getApplicationContext(),Giohangsp.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}