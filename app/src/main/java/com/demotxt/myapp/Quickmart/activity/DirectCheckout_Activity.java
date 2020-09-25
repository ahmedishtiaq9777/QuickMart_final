package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.DetailModel;
import com.demotxt.myapp.Quickmart.ownmodels.ShippingModel;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.ownmodels.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DirectCheckout_Activity extends AppCompatActivity {

    EditText UserName, UserPhone, UserAddress;
    RadioButton COD;
    Button Confirm;
    ImageView mImageView;
    TextView name,price,EditCheckout_Btn;
    String N,P,I,pId,sId,size,color;
    int Code = 111;
    String Uname,Ucontact,Uaddress,Userid;
    SharedPreferences loginpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_checkout_);
        //
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        Userid = String.valueOf(loginpref.getInt("userid", 0));
        //
        UserName = findViewById(R.id.name);
        UserPhone = findViewById(R.id.phoneNumber);
        UserAddress = findViewById(R.id.address);
        COD = findViewById(R.id.Cod_Rd);
        Confirm = findViewById(R.id.ConfirmBtn);
        mImageView = findViewById(R.id.imageView5);
        name = findViewById(R.id.txt_P_Name);
        price = findViewById(R.id.txt_P_Price);
        EditCheckout_Btn = findViewById(R.id.editCheckout_btn);

        //product detail
        Intent i = getIntent();
        N = i.getExtras().getString("name");
        name.setText(N);
        P = i.getExtras().getString("price");
        price.setText(P);
        I = i.getExtras().getString("img");
        Picasso.get().load(I).into(mImageView);
        pId = i.getExtras().getString("proId");
        size=i.getExtras().getString("size");
        color=i.getExtras().getString("color");
        sId = i.getExtras().getString("sellerId");

        //
        final DetailModel obj = new DetailModel(getApplicationContext());
        //
        Uname = obj.getYourName();
        UserName.setText(obj.getYourName());
        UserName.setEnabled(false);
        Uaddress = obj.getYourAddress();
        UserAddress.setText(obj.getYourAddress());
        UserAddress.setEnabled(false);
        Ucontact = obj.getYourPhone();
        UserPhone.setText(obj.getYourPhone());
        UserPhone.setEnabled(false);
        //
if(!Uname.equals("Your Name")||!Uaddress.equals("Your Address")) {
    //Saving Shipping Detail
    ShippingModel m = new ShippingModel(Uname, "", Ucontact, Uaddress, "", "Sialkot");
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String shipping_detail = gson.toJson(m);
    String url = MainActivity2.hostinglink + "/home/SaveShippingDetail";
    SaveShippingDetail(url, shipping_detail);
}else {
    Intent ch = new Intent(DirectCheckout_Activity.this,Detail_Activity.class);
    startActivity(ch);
}
        //Edit Checkout Detail
        EditCheckout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ch = new Intent(DirectCheckout_Activity.this,Detail_Activity.class);
                startActivity(ch);
            }
        });


        // Intent to confirmation
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sh = new Intent(DirectCheckout_Activity.this,Direct_Confirmation.class);
                sh.putExtra("img",I);
                sh.putExtra("name",N);
                sh.putExtra("price",Double.valueOf(P));
                sh.putExtra("UserName",obj.getYourName());
                sh.putExtra("UserContact",obj.getYourPhone());
                sh.putExtra("UserAdd",obj.getYourAddress());
                sh.putExtra("proId",pId);
                sh.putExtra("sellerId",sId);
                sh.putExtra("size",size);
                sh.putExtra("color",color);
                startActivity(sh);
            }
        });


    }

    //sending shipping detail
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