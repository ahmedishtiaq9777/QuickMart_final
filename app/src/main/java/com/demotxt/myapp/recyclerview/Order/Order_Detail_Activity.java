package com.demotxt.myapp.recyclerview.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.CategoryFragments.Catkids;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.demotxt.myapp.recyclerview.ownmodels.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_Detail_Activity extends AppCompatActivity {

    TextView txt_price,txt_status,txt_id,txt_date;
    ProgressBar mProgressBar;

    //For Cartlist
    private RecyclerView mRecyclerView;
    private Order_Detail_Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Order_Detail> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__detail_);

        mList = new ArrayList<>();

        //Dummy Data
/*
        mList.add(new Order_Detail("Shoes",15,"2",R.drawable.image_1));
        mList.add(new Order_Detail("Shirt",25,"1",R.drawable.image_2));
        mList.add(new Order_Detail("Tie",5,"1",R.drawable.image_3));
        mList.add(new Order_Detail("Pant",20,"1",R.drawable.image_4));

 */
                //INIT
        txt_id = findViewById(R.id.txt_id);
        txt_status = findViewById(R.id.txt_status);
        txt_price = findViewById(R.id.txt_price);
        txt_date = findViewById(R.id.txt_Date);
        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.order_detail_products);

        //Receive Data
        Intent intent = getIntent();
        String Price = intent.getExtras().getString("Price");
        String status = intent.getExtras().getString("Status");
        String id = intent.getExtras().getString("ID");
        String date = intent.getExtras().getString("Date");

        //
        //Setting Values on Text Views
        txt_id.setText(id);
        txt_date.setText(date);
        txt_price.setText(Price);
        txt_status.setText(status);

        //Progressbar conditions
        if (status.equals("completed")){
            mProgressBar.setProgress(100);
        }
        else if (status.equals("ongoing")){
            mProgressBar.setProgress(50);
        }
        else if (status.equals("waiting")){
            mProgressBar.setProgress(25);
        }

       // setAdapterRecyclerView();

        try{
            getconnection("http://ahmedishtiaq1997-001-site1.ftempurl.com/Home/GetOrderItems", id);
            //getconnection("http://ahmedishtiaq1997-001-site1.ftempurl.com/Home/getrecommendedpro",id);

        }catch (Exception e) {

            Log.e("error",e.getMessage());

        }
    }
    //Get Connection
    public void getconnection(String url,final String Order_Id) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
                        try {
                            //Toast.makeText(getApplicationContext(),"responce"+response,Toast.LENGTH_SHORT).show();
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();

                            mList = Arrays.asList(gson.fromJson(response, Order_Detail[].class));
                           setimageurl();
                            setAdapterRecyclerView();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"error:"+error,Toast.LENGTH_LONG).show();
                       // Log.e("error",error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderid", Order_Id);

                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        request.add(rRequest);

    }
   /* public void getconnection(String url) {
        final RequestQueue request = Volley.newRequestQueue(this);

        StringRequest rRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            mList = Arrays.asList(gson.fromJson(response, Product[].class));

                            setAdapterRecyclerView();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"error"+error,Toast.LENGTH_LONG).show();
                        // Log.e("error",error.getMessage());
                    }
                }

        );
        request.add(rRequest);


}*/
   private void setimageurl() {
       int n = 0;
       for (Order_Detail i :mList ) {
           i.setImage("http://ahmedishtiaq1997-001-site1.ftempurl.com" + i.getImage());
           // list.remove(n);
           mList.set(n, i);
           n++;


       }
   }
    //for Recycler View
    private void setAdapterRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Order_Detail_Adapter(getApplicationContext(),mList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


}
