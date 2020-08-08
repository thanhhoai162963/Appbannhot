package com.example.appbanhangv2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangv2.R;

import java.util.HashMap;
import java.util.Map;



public class ThongtinKH extends AppCompatActivity {
    EditText medtTenKh,medtDiachi, medtSodt, medtEmail;
    Button mbtnTienhanhdathang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_k_h);
        Mapping();
    }
    public void Mapping(){
        medtTenKh = findViewById(R.id.edtTenKh);
        medtDiachi = findViewById(R.id.edtDiachi);
        medtSodt = findViewById(R.id.edtSodt);
        medtEmail = findViewById(R.id.edtEmail);
        mbtnTienhanhdathang = findViewById(R.id.btnTienHanhdathang);
    }

    @Override
    protected void onStart() {
        Click();

        super.onStart();
    }
    public void Click(){
        mbtnTienhanhdathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenKh = medtTenKh.getText().toString().trim();
                final String soDt = medtSodt.getText().toString().trim();
                final String diachi = medtDiachi.getText().toString().trim();
                final String email = medtEmail.getText().toString().trim();
                if (tenKh.length()>0 && soDt.length()>0 && diachi.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.DuongdanDonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("madonhang",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",tenKh);
                            hashMap.put("sodienthoai",soDt);
                            hashMap.put("diachi",diachi);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(ThongtinKH.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ThongtinKH.this, "ban hãy kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}