package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.adapter.OrderViewAdapter;
import com.demotxt.myapp.recyclerview.ownmodels.OrderViewImg;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demotxt.myapp.recyclerview.fragment.CartFragment.list;


public class Confirmation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button confirm;
    TextView t1,t2;
    List<OrderViewImg> orderViewImgs;
    ArrayList<CartListBeanlist> prolist;
    SharedPreferences loginpref;
    String Userid;
    double  total;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        confirm=(Button)findViewById(R.id.button3);
        t1 = (TextView)findViewById(R.id.getname);
        t2 = (TextView)findViewById(R.id.getaddress);

        Bundle bn = getIntent().getExtras();
        String name = bn.getString("getname");
        String address = bn.getString("getaddress");
        t1.setText(String.valueOf(name));
        t2.setText(String.valueOf(address));



         orderViewImgs = new ArrayList<>();
         prolist=new ArrayList<CartListBeanlist>();
         total=0.0;
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        Userid=String.valueOf(loginpref.getInt("userid",0));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("Bundlelist");
       prolist  = (ArrayList<CartListBeanlist>) args.getSerializable("list");

        for (CartListBeanlist i:prolist ) {
            OrderViewImg orderViewImg=new OrderViewImg(i.getImage(),i.getTitle(),i.getQuantity(),i.getPrice());
            orderViewImgs.add(orderViewImg);
            double oneproducttotal=0.0;
         oneproducttotal = i.getQuantity()*i.getPrice();
         total=total+oneproducttotal;


        }
        Toast.makeText(getApplicationContext(),"Total:"+total,Toast.LENGTH_LONG).show();


/*
        List<OrderViewImg> orderViewImgs = new ArrayList<>();
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
        orderViewImgs.add(new OrderViewImg(R.drawable.shoes,"SHirt",2,1000));
*/

        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderViewAdapter(orderViewImgs);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


























GsonBuilder builder=new GsonBuilder();
Gson gson=builder.create();
        Type listOfTestObject = new TypeToken<List<CartListBeanlist>>(){}.getType();
           final String s = gson.toJson(list, listOfTestObject);


       /* JSONArray jsonArray= new JSONArray();
        for (OrderViewImg  i:orderViewImgs) {
            jsonArray.put(i);
        }*/
        try {
            Log.i("Product Arraylist:",s);
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


SaveOrder("http://ahmedishtiaq1997-001-site1.ftempurl.com/home/SaveOrder",s);


            }
        });
    }
    public void SaveOrder(String Url, final String productsarray){
        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";



            StringRequest rRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                GsonBuilder builder=new GsonBuilder();
                                Gson gson=builder.create();
                                StringResponceFromWeb result=gson.fromJson(response,StringResponceFromWeb.class);
                               // Toast.makeText(getApplicationContext(),result.getresult(),Toast.LENGTH_SHORT).show();


                                try{
                                    String error=result.getErrorResult();
                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                                    Log.i("Error In Shipping :",error);

                                }catch (NullPointerException E)
                                {
                                    Log.i("Orderconformed ..","Order is Conformed");
                                    Intent i = new Intent(getBaseContext(), AnimationOrder.class);
                                    startActivity(i);
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
                            Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userid",Userid);

                //    JSONArray jsonArray= new JSONArray();

                  // jsonArray.put(productsarray);
                    params.put("orderedproducts",productsarray);
                    params.put("total",String.valueOf(total));


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
