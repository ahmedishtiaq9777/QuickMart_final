package com.demotxt.myapp.Quickmart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.Cart_Fav.CartListBeanlist;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.adapter.OrderViewAdapter;
import com.demotxt.myapp.Quickmart.fragment.CartFragment;
import com.demotxt.myapp.Quickmart.ownmodels.OrderViewImg;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Direct_Confirmation extends AppCompatActivity {
    Button confirm;
    TextView t1, t2, p;
    ImageView mImageView;
    TextView Pname,Pprice;
    ArrayList<CartListBeanlist> prolist;
    SharedPreferences loginpref;
    String Userid,name,address,color,size;
    int pId,sId;
    double total;
    int code;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct__confirmation);

        confirm = (Button) findViewById(R.id.button3);
        t1 = (TextView) findViewById(R.id.getname);
        t2 = (TextView) findViewById(R.id.getaddress);
        p = (TextView) findViewById(R.id.TOTAL);
        Pname = findViewById(R.id.txtName);
        Pprice = findViewById(R.id.txtPrice);
        mImageView = findViewById(R.id.imageV);

        total = 0.0;
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        Userid = String.valueOf(loginpref.getInt("userid", 0));

        //Intent values From DirectCheckout
        Intent DC = getIntent();
        name = DC.getExtras().getString("UserName");
        address =  DC.getExtras().getString("UserAdd");
        String i = DC.getExtras().getString("proId");
        String sell = DC.getExtras().getString("sellerId");
        size=  DC.getExtras().getString("size");
        color=DC.getExtras().getString("color");
        pId = Integer.parseInt(i);
        sId = Integer.parseInt(sell);
        //
        t1.setText(name);
        t2.setText(address);
        Pname.setText(DC.getExtras().getString("name"));
        double pt = DC.getExtras().getDouble("price");
        Pprice.setText(String.valueOf(pt));
        Picasso.get().load(DC.getExtras().getString("img")).into(mImageView);

        //
        total = DC.getExtras().getDouble("price");
        p.setText(String.valueOf(total));


        confirm = (Button) findViewById(R.id.button3);
        t1 = (TextView) findViewById(R.id.getname);
        t2 = (TextView) findViewById(R.id.getaddress);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listOfTestObject = new TypeToken<List<CartListBeanlist>>() {
        }.getType();
        List<CartListBeanlist> templist=new ArrayList<CartListBeanlist>() ;


        CartListBeanlist ob = new CartListBeanlist();
        ob.setImage(DC.getExtras().getString("img"));
        ob.setTitle(DC.getExtras().getString("name"));
        ob.setPrice(DC.getExtras().getDouble("price"));
        ob.setQuantity(1);
        ob.setId(pId);
        ob.setSellerid(sId);

        templist.add(ob);


        final String s = gson.toJson(templist, listOfTestObject);



        try {
            Log.i("Product Arraylist:", s);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveOrder(MainActivity2.hostinglink + "/home/SaveOrderForDidectBuy", s);

            }
        });
    }

    public void SaveOrder(String Url, final String productsarray) {
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";
            StringRequest rRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                StringResponceFromWeb result = gson.fromJson(response, StringResponceFromWeb.class);
                                // Toast.makeText(getApplicationContext(),result.getresult(),Toast.LENGTH_SHORT).show();
                                try {
                                    String error = result.getErrorResult();
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                    Log.i("Error In Shipping :", error);
                                } catch (NullPointerException E) {
                                    Log.i("Orderconformed ..", "Order is Conformed");
                                    Intent i = new Intent(getBaseContext(), AnimationOrder.class);
                                    startActivity(i);
                                    //notification function
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                                Toast.makeText(getApplicationContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.i("In OnerrorResponce", error.getMessage());
                            Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userid", Userid);
                    params.put("orderedproducts", productsarray);
                    params.put("total", String.valueOf(total));
                    params.put("color",color);
                    params.put("size",size);
                    params.put("proid",String.valueOf(pId));
                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            requestQueue.add(rRequest);
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), "Error: " + E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}