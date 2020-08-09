package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.recyclerview.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;

public class Signup extends AppCompatActivity {

    TextView signin;
    Button signup;
    EditText email, password, userName;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);
        //INIT
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Signup.this, R.id.usernamee, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Signup.this, R.id.emaill, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
        awesomeValidation.addValidation(Signup.this, R.id.passwordd, RegexTemplate.NOT_EMPTY, R.string.error_password);

        //INITIALIZE
        signin = (TextView) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signin1);
        userName = (EditText) findViewById(R.id.usernamee);
        email = (EditText) findViewById(R.id.emaill);
        password = (EditText) findViewById(R.id.passwordd);
        //
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    // String url = "http:// 192.168.10.13:64077/api/login";
                    //String url="https://api.myjson.com/bins/kp9wz";
                    String url = hostinglink + "/Home/signup";

                    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
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
                                        Log.i("API-ERROR", "error");
                                        Toast.makeText(getApplicationContext(), "error" + response.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.i("API-ERROR", error.getMessage());
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("email", email.getText().toString());
                            params.put("password", password.getText().toString());
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
}

