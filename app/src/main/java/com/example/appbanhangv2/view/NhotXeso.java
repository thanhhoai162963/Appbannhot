package com.example.appbanhangv2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangv2.adapter.NhotXesoAdapter;
import com.example.appbanhangv2.model.Sanpham;
import com.example.appbanhangv2.ultil.CheckConnection;
import com.example.appbanhangv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NhotXeso extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mToolbar;
    RecyclerView mRcvNhotxeso;
    NhotXesoAdapter nhotXesoAdapter;
    ArrayList<Sanpham> arrayListNhotxeso;
    int idNhotxeso =0;
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhot_xeso);
        Mapping();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLoaiSp();
           // ActionToolbar();
            GetData(page);
        }else {
            CheckConnection.showToast(getApplicationContext(),"bạn kiểm tra lại kết nối");
            finish();
        }

    }
    private void GetData(int Page){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Sever.DuongdanNhotXeSo+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String TensanphamNhotxeso = "";
                int GiasanphamNhotxeso = 0;
                String HinhsanphamNhotxeso = "";
                String MotasanphamNhotxeso = "";
                int IDsanphamNhotxeso = 0;
                if (response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            TensanphamNhotxeso = jsonObject.getString("tensp");
                            GiasanphamNhotxeso = jsonObject.getInt("giasp");
                            HinhsanphamNhotxeso = jsonObject.getString("hinhanhsp");
                            MotasanphamNhotxeso = jsonObject.getString("motaso");
                            IDsanphamNhotxeso = jsonObject.getInt("idsanpham");
                            arrayListNhotxeso.add(new Sanpham(ID,TensanphamNhotxeso,GiasanphamNhotxeso,HinhsanphamNhotxeso,MotasanphamNhotxeso,IDsanphamNhotxeso));
                            nhotXesoAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idNhotxeso));
                return param;

            }
        };
        requestQueue.add(stringRequest);
    }
    private void GetIdLoaiSp(){
        idNhotxeso = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatri ",idNhotxeso+"");

    }
  /*  private void ActionToolbar(){
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

    public void setSupportActionBar(Toolbar mToolbar) {
    }

    public void Mapping(){
        mToolbar = findViewById(R.id.toolbarNhotXeso);
        setSupportActionBar(mToolbar);
        mRcvNhotxeso=findViewById(R.id.recyclerviewNhotxeso);
        arrayListNhotxeso = new ArrayList<>();
        nhotXesoAdapter = new NhotXesoAdapter(getApplicationContext(),arrayListNhotxeso);
        mRcvNhotxeso.setAdapter(nhotXesoAdapter);

    }
}