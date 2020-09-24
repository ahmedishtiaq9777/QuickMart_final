package com.demotxt.myapp.Quickmart.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Order_Activity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Order_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Order> mOrderList;
    private SharedPreferences login;
    String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);

        mOrderList = new ArrayList<>();
/*

        mOrderList.add(new Order("1","23/6/20","ongoing","50$"));
        mOrderList.add(new Order("2","23/6/20","completed","30$"));
        mOrderList.add(new Order("3","23/6/20","completed","20$"));
        mOrderList.add(new Order("4","23/6/20","ongoing","65$"));
*/

        mRecyclerView = findViewById(R.id.Order_Recyclerview);
        //Id
        login = getSharedPreferences("loginpref", MODE_PRIVATE);
        Uid = String.valueOf(login.getInt("userid", 0));
        //
        getconnection(hostinglink +"/Home/GetOrdersOfUser", Uid);
    }

    //Get Connection
    public void getconnection(String url, final String User_Id) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            mOrderList = Arrays.asList(gson.fromJson(response, Order[].class));

                            setAdapterRecyclerView();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", User_Id);

                return params;
            }

            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        request.add(rRequest);

    }

    private void setAdapterRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Order_Adapter(getApplicationContext(), mOrderList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }
}
