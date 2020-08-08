package com.example.appbanhangv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangv2.adapter.LoaiSpAdapter;
import com.example.appbanhangv2.model.Loaisp;
import com.example.appbanhangv2.ultil.CheckConnection;
import com.example.appbanhangv2.R;
import com.example.appbanhangv2.adapter.SanphamAdapter;
import com.example.appbanhangv2.model.Sanpham;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<com.example.appbanhangv2.model.Giohang> arrayListGiohang = new ArrayList<>();
    androidx.appcompat.widget.Toolbar mToolbar;
    ViewFlipper mviewFlipper;
    RecyclerView mRecyclerViewManhinhChinh;
    NavigationView mNavigationView;
    ListView mListViewManhinhChinh;
    DrawerLayout mDrawerLayout;
    ArrayList<Loaisp> arrayListLoaiSp;
    ArrayList<Sanpham> arrayListSanpham;
    SanphamAdapter sanphamAdapter;
    LoaiSpAdapter loaiSpAdapter;
    int id =0 ;
    String tenloaisp = "";
    String hinhanhloaisp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        if (CheckConnection.haveNetworkConnection((getApplicationContext()))){
            ActionBar();
            ActionViewFlliper();
            getLoaiSP();
            getDataSanphamMoinhat();
            CatchOnItemListview();
        }else {
            CheckConnection.showToast(getApplicationContext(),"bạn kiểm tra lại kết nối");
            finish();
        }
    }
    private void CatchOnItemListview(){
        mListViewManhinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.haveNetworkConnection((getApplicationContext()))) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"kiểm tra lại kết nối");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection((getApplicationContext()))) {
                            Intent intent = new Intent(MainActivity.this, NhotXeso.class);
                            intent.putExtra("idloaisanpham",arrayListLoaiSp.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"kiểm tra lại kết nối");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection((getApplicationContext()))) {
                            Intent intent = new Intent(MainActivity.this, NhotXetayga.class);
                            intent.putExtra("idloaisanpham",arrayListLoaiSp.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"kiểm tra lại kết nối");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }
    public void getDataSanphamMoinhat(){
        RequestQueue  requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(com.example.appbanhangv2.view.Sever.DuongdanSanphamMoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhsanpham = "";
                    String Motasanpham = "";
                    int IDsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motaso");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            arrayListSanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    public void getLoaiSP(){
        RequestQueue  requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(com.example.appbanhangv2.view.Sever.DuongdanLoaisp, response -> {
            if(response != null){
                for (int i=0; i< response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tenloaisp =jsonObject.getString("tenloaisp");
                        hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                        arrayListLoaiSp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                        loaiSpAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void ActionViewFlliper(){
        ArrayList<String> arrayListQuangcao = new ArrayList<>();
        arrayListQuangcao.add("https://nhotmotul.weebly.com/uploads/3/9/6/1/39613433/423330178.jpg?416");
        arrayListQuangcao.add("https://tailocnguyen.vn/uploads/news/2015_03/shell-advance.jpg");
        arrayListQuangcao.add("https://anh.24h.com.vn/upload/1-2016/images/2016-02-03/1454466319-chai-nhot-vang-tim-thay-chu--1-.jpg");
        arrayListQuangcao.add("https://znews-photo.zadn.vn/w660/Uploaded/wyhktpu/2017_10_31/3_1.jpg");
        for(int i =0; i<arrayListQuangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayListQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mviewFlipper.addView(imageView);
        }
        mviewFlipper.setFlipInterval(3000);
        mviewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        mviewFlipper.setInAnimation(animation_slide_in);
        mviewFlipper.setOutAnimation(animation_slide_out);
    }
    private void ActionBar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_menu_open_24);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setSupportActionBar(Toolbar mToolbar) {
    }


    public void Mapping(){
        mToolbar = findViewById(R.id.toolbarManhinhChinh);
        mviewFlipper = findViewById(R.id.viewLipper);
        mRecyclerViewManhinhChinh = findViewById(R.id.Rcv);
        mNavigationView = findViewById(R.id.navigation_view);
        mListViewManhinhChinh = findViewById(R.id.listviewmanhinh);
        mDrawerLayout =findViewById(R.id.drawerLayout);
        arrayListLoaiSp = new ArrayList<>();
        arrayListLoaiSp.add(0, new Loaisp(0,"Trang chính","https://img.icons8.com/cute-clipart/240/home.png"));
        loaiSpAdapter = new LoaiSpAdapter(arrayListLoaiSp,getApplicationContext());
        mListViewManhinhChinh.setAdapter(loaiSpAdapter);
        arrayListSanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),arrayListSanpham);
        mRecyclerViewManhinhChinh.setHasFixedSize(true);
        mRecyclerViewManhinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2 ));
        mRecyclerViewManhinhChinh.setAdapter(sanphamAdapter);
    }


}