package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.fragment.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class User_Detail_Activity extends AppCompatActivity {

    private EditText Name,Email,Address,Phone,Password,ConfirmPass;
    private Button Cancel,Confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__detail_);

        //initialize
        Name = findViewById(R.id.YourName);
        Email = findViewById(R.id.YourEmail);
        Address = findViewById(R.id.YourAddress);
        Phone = findViewById(R.id.YourPhone);
        Password = findViewById(R.id.YourPass);
        ConfirmPass = findViewById(R.id.YourPassConfirm);

        Cancel = findViewById(R.id.Cancel_Btn);
        Confirm = findViewById(R.id.Confirm_Btn);


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting user detail
                try {
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    // String url = "http:// 192.168.10.13:64077/api/login";
                    //String url="https://api.myjson.com/bins/kp9wz";
                    String url = "http://ahmedishtiaq9778-001-site1.ftempurl.com/home/getuserwithid";

                    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    //get values from user table

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.i("APIERROR", error.getMessage());
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

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
                    Log.i("error", E.getMessage());
                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Detail_Activity.this, ProfileFragment.class);
                startActivity(intent);
            }
        });

    }


}