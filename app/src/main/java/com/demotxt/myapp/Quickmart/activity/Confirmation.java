package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.Cart_Fav.CartListBeanlist;
import com.demotxt.myapp.Quickmart.fragment.CartFragment;
import com.demotxt.myapp.Quickmart.ownmodels.OrderViewImg;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.adapter.OrderViewAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class Confirmation extends AppCompatActivity {
    private final String CHANNEL_ID = "Notification";
    private final int NOTIFICATION_ID = 001;
    private RecyclerView recyclerView;
    private OrderViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button confirm;
    TextView t1, t2, p;
    List<OrderViewImg> orderViewImgs;
    ArrayList<CartListBeanlist> prolist;
    SharedPreferences loginpref;
    String Userid, name, address;
    String pId, sId;
    double total;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        confirm = (Button) findViewById(R.id.button3);
        t1 = (TextView) findViewById(R.id.getname);
        t2 = (TextView) findViewById(R.id.getaddress);
        p = (TextView) findViewById(R.id.TOTAL);

        Bundle bn = getIntent().getExtras();
        name = bn.getString("getname");
        address = bn.getString("getaddress");

        orderViewImgs = new ArrayList<>();
        prolist = new ArrayList<CartListBeanlist>();
        total = 0.0;
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        Userid = String.valueOf(loginpref.getInt("userid", 0));
        Intent intent = getIntent();
        //
        try {
            Bundle args = intent.getBundleExtra("Bundlelist");
            prolist = (ArrayList<CartListBeanlist>) args.getSerializable("list");
        } catch (Exception E) {
            Toast.makeText(this, "Serializable Error", Toast.LENGTH_SHORT).show();
        }

        t1.setText(name);
        t2.setText(address);

///////////////////////
        for (CartListBeanlist i : prolist) {
            OrderViewImg orderViewImg = new OrderViewImg(i.getImage(), i.getTitle(), i.getQuantity(), i.getPrice());
            orderViewImgs.add(orderViewImg);
            double oneproducttotal = 0.0;
            oneproducttotal = i.getQuantity() * i.getPrice();
            total = total + oneproducttotal;
        }

        p.setText(String.valueOf(total));


        //
        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderViewAdapter(orderViewImgs);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        confirm = (Button) findViewById(R.id.button3);
        t1 = (TextView) findViewById(R.id.getname);
        t2 = (TextView) findViewById(R.id.getaddress);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listOfTestObject = new TypeToken<List<CartListBeanlist>>() {
        }.getType();
        final String s = gson.toJson(CartFragment.list, listOfTestObject);


        try {
            Log.i("Product Arraylist:", s);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveOrder(MainActivity2.hostinglink + "/home/SaveOrder", s);
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
                                    SetNotification();
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

                    //    JSONArray jsonArray= new JSONArray();

                    // jsonArray.put(productsarray);
                    params.put("orderedproducts", productsarray);
                    params.put("total", String.valueOf(total));
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

    //setting notification
    public void SetNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo3);
        builder.setContentTitle("Order");
        builder.setContentText("We've Got Your Order Please Wait For Confirmation ..");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
