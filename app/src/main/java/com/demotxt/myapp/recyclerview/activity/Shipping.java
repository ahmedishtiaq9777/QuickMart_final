package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.ShippingModel;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.recyclerview.shoppycartlist.CartListBeanlist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;

public class Shipping extends AppCompatActivity {

    EditText t1,t2,t3,t4,t5;
    Spinner city;
    Button ship;
    AwesomeValidation awesomeValidation;
    ArrayList<CartListBeanlist> list;
    SharedPreferences loginpref;
    String Userid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping);
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
Userid=String.valueOf(loginpref.getInt("userid",0));
        Intent intent = getIntent();
       // String s=intent.getExtras().getString("cartlist");
        //GsonBuilder builder=new GsonBuilder();
        //Gson gson=builder.create();
        //Type listOfTestObject = new TypeToken<List<CartListBeanlist>>(){}.getType();
        //list= gson.fromJson(s, listOfTestObject);
        Bundle args = intent.getBundleExtra("bundle");
         list = (ArrayList<CartListBeanlist>) args.getSerializable("cartlist");
        awesomeValidation = new AwesomeValidation(BASIC);



        awesomeValidation.addValidation(Shipping.this, R.id.nameShip, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Shipping.this, R.id.contactShip, "^0(?=3)[0-9]{10}$", R.string.error_contact);
        awesomeValidation.addValidation(Shipping.this, R.id.emailShip, android.util.Patterns.EMAIL_ADDRESS.toString(), R.string.error_email);
        awesomeValidation.addValidation(Shipping.this, R.id.contactShip, "^[0-9]{11}", R.string.error_contact);
        awesomeValidation.addValidation(Shipping.this, R.id.addShip, RegexTemplate.NOT_EMPTY, R.string.error_address);
        awesomeValidation.addValidation(Shipping.this, R.id.zipShip, "^[0-9]{5}", R.string.error_zip);



        ship=(Button)findViewById(R.id.button1);
        t1=(EditText) findViewById(R.id.nameShip);
        t2=(EditText) findViewById(R.id.emailShip);
        t3=(EditText) findViewById(R.id.contactShip);
        t4=(EditText) findViewById(R.id.addShip);
        t5=(EditText) findViewById(R.id.zipShip);






        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(awesomeValidation.validate()) {


                    String name = t1.getText().toString();
                    String email = t2.getText().toString();
                    String contact = t3.getText().toString();
                    String address = t4.getText().toString();
                    String code = t5.getText().toString();
                    String cty=String.valueOf(city.getSelectedItem());
                    ShippingModel m=new ShippingModel(name,email,contact,address,code,cty);

                    GsonBuilder builder=new GsonBuilder();
                  Gson gson=builder.create();
                  String shipping_detail=gson.toJson(m);
                  String url=hostinglink +"/home/SaveShippingDetail";
                  SaveShippingDetail(url,shipping_detail);
                    Intent i = new Intent(getBaseContext(), Confirmation.class);
                    i.putExtra("getname", name);
                    i.putExtra("getaddress", address);

                    Bundle args = new Bundle();
                    args.putSerializable("list",(Serializable)list);
                    i.putExtra("Bundlelist",args);

                    startActivity(i);

                }else {

                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
     private   void SaveShippingDetail(String Url, final String ShippingDetail){





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
                  Toast.makeText(getApplicationContext(),result.getresult(),Toast.LENGTH_SHORT).show();


                  try{
                      String error=result.getErrorResult();
                      Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                  Log.i("Error In Shipping :",error);

                  }catch (NullPointerException E)
                  {
                      Log.i("Shipping ..","Shipping Detail  is Saved in DB");
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
                     params.put("shippingdetail",ShippingDetail);
                     params.put("userid",Userid);

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
