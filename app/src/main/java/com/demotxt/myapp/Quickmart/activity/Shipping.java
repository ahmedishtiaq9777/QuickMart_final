package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.demotxt.myapp.Quickmart.Cart_Fav.CartListBeanlist;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;
import com.demotxt.myapp.Quickmart.ownmodels.ShippingModel;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class Shipping extends AppCompatActivity {

    EditText t1, t3, t4;
    TextView city,editCheckout_Btn;
    Button ship;
    AwesomeValidation awesomeValidation;
    @androidx.annotation.Nullable
    ArrayList<CartListBeanlist> list;
    SharedPreferences loginpref;
    String Userid,name,contact,address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping);
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        Userid = String.valueOf(loginpref.getInt("userid", 0));
        Intent intent = getIntent();
        city = findViewById(R.id.city);

        Bundle args = intent.getBundleExtra("bundle");
        list = (ArrayList<CartListBeanlist>) args.getSerializable("cartlist");
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Shipping.this, R.id.nameShip, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Shipping.this, R.id.contactShip, "^0(?=3)[0-9]{10}$", R.string.error_contact);
        awesomeValidation.addValidation(Shipping.this, R.id.addShip, RegexTemplate.NOT_EMPTY, R.string.error_address);

        ship = (Button) findViewById(R.id.button1);
        t1 = (EditText) findViewById(R.id.nameShip);
        t3 = (EditText) findViewById(R.id.contactShip);
        t4 = (EditText) findViewById(R.id.addShip);
        editCheckout_Btn = findViewById(R.id.editCheckout_btn);
        //
        final DetailModel obj = new DetailModel(getApplicationContext());
        //
        t1.setText(obj.getYourName());
        t1.setEnabled(false);
        t4.setText(obj.getYourAddress());
        t4.setEnabled(false);
        t3.setText(obj.getYourPhone());
        t3.setEnabled(false);
        //

        //

        //Edit Checkout Detail
        editCheckout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ch = new Intent(Shipping.this,Detail_Activity.class);
                startActivity(ch);
            }
        });


        //
        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    String cty = city.getText().toString();
                    //
                    DetailModel model = new DetailModel(getApplicationContext());
                    name = model.getYourName();
                    t1.setText(name);
                    contact = model.getYourPhone();
                    t3.setText(contact);
                    address = model.getYourAddress();
                    t4.setText(address);
                    //

                    ShippingModel m = new ShippingModel(name, "", contact, address, "", cty);
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    String shipping_detail = gson.toJson(m);
                    String url = MainActivity2.hostinglink + "/home/SaveShippingDetail";
                    SaveShippingDetail(url, shipping_detail);

                    //
                    Intent i = new Intent(getBaseContext(), Confirmation.class);
                    i.putExtra("getname", name);
                    i.putExtra("getaddress", address);
                    Bundle args = new Bundle();
                    args.putSerializable("list", (Serializable) list);
                    i.putExtra("Bundlelist", args);
                    startActivity(i);
                }
            }
        });
    }

    private void SaveShippingDetail(String Url, final String ShippingDetail) {
        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest rRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                StringResponceFromWeb result = gson.fromJson(response, StringResponceFromWeb.class);
                                Toast.makeText(getApplicationContext(), result.getresult(), Toast.LENGTH_SHORT).show();
                                try {
                                    String error = result.getErrorResult();
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                    Log.i("Error In Shipping :", error);
                                } catch (NullPointerException E) {
                                    Log.i("Shipping ..", "Shipping Detail  is Saved in DB");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(@androidx.annotation.NonNull VolleyError error) {
                            // error
                            Log.i("In OnerrorResponce", error.getMessage());
                            Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("shippingdetail", ShippingDetail);
                    params.put("userid", Userid);
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
