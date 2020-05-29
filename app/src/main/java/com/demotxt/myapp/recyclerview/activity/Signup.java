package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.recyclerview.R;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView signin, signup;
    EditText email, password;
    Spinner spiner;
    public String selectedaccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);
//INITIALLIZE
        signin = (TextView) findViewById(R.id.signin);
        signup = (TextView) findViewById(R.id.signin1);
        email = (EditText) findViewById(R.id.emaill);
        password = (EditText) findViewById(R.id.passwordd);
        spiner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Accounttypes));
        listadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(listadapter);
        spiner.setOnItemSelectedListener(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    // String url = "http:// 192.168.10.13:64077/api/login";
                    //String url="https://api.myjson.com/bins/kp9wz";
                    String url = "http://ahmedishtiaq9778-001-site1.ftempurl.com/Home/signup";

                    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                    int size = response.toString().length();

                                    String result = response.toString().substring(1, size - 1);


                                    if (result.toString().equals("registered")) {
                                        finish();
                                        Intent intent = new Intent(Signup.this, MainActivity2.class);

                                        startActivity(intent);
                                    }
                                    if (result.toString().equals("alreadyregistered")) {
                                        Toast.makeText(getApplicationContext(), "This Email is Already Registered", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.i("APIERROR", "error");
                                        Toast.makeText(getApplicationContext(), "error" + response.toString(), Toast.LENGTH_SHORT).show();

                                    }
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
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("email", email.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("UserType", selectedaccount);
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
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Signup.this, Login.class);
                startActivity(it);

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedaccount = parent.getItemAtPosition(position).toString();
        if (selectedaccount.equals("As a Buyer")) {
            selectedaccount = "C";

        } else if (selectedaccount.equals("As a Seller")) {

            selectedaccount = "S";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
