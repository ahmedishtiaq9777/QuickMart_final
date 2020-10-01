package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Forgot_Pass_Activity extends AppCompatActivity {

    EditText PhoneNo, OTP_Text;
    Button Forget_Btn, SendOTP;
    String pin;
    int randomNumber;
    final int range = 9;
    final int length = 4;
    int val;
    int userPin;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    private StringResponceFromWeb result;
    ConstraintLayout Lyt_OTP, Lyt_Phone;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass_);
        //
        result = new StringResponceFromWeb();

        mPreferences = getApplicationContext().getSharedPreferences("ForgetOTP", MODE_PRIVATE);
        mEditor = mPreferences.edit();
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Forgot_Pass_Activity.this, R.id.Txt_PhoneNo, "^0(?=3)[0-9]{10}$", R.string.error_contact);

        PhoneNo = findViewById(R.id.Txt_PhoneNo);
        SendOTP = findViewById(R.id.SendOTP_Btn);
        Forget_Btn = findViewById(R.id.Submit_OTP);
        OTP_Text = findViewById(R.id.Pin);
        Lyt_Phone = findViewById(R.id.lyt_forget);
        Lyt_OTP = findViewById(R.id.lyt_OTP);

        //Send OTP on Phone No.
        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                    CheckUser();
                }
            }
        });

        OTP_Text.addTextChangedListener(OTPTextWatcher);


        //Forget button
        Forget_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    //getting pin
                    try {
                        String pin = OTP_Text.getText().toString();
                        userPin = Integer.parseInt(pin);
                    } catch (Exception e) {
                        Toast.makeText(Forgot_Pass_Activity.this, "UserPin Empty", Toast.LENGTH_SHORT).show();
                    }

                    //Shared Pref for OTP PIN
                    try {
                        val = mPreferences.getInt("OTP_PIN", 0);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (userPin == val) {
                        ForgetPass();
                    } else {
                        Toast.makeText(getApplicationContext(), "Pin didn't match", Toast.LENGTH_SHORT).show();
                        Lyt_OTP.setVisibility(View.GONE);
                        Lyt_Phone.setVisibility(View.VISIBLE);
                    }
            }
        });
    }

    private final TextWatcher OTPTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pin = OTP_Text.getText().toString().trim();
            Forget_Btn.setEnabled(!pin.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //reset user password
    private void ForgetPass() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = hostinglink + "/Home/forgetpassword";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);
                            if(result.getresult().equals("NotRegistered")) {

                            } else {
                                String var = result.getresult();
                                Intent forg = new Intent(Forgot_Pass_Activity.this, Password_Activity.class);
                                forg.putExtra("password", var);
                                forg.putExtra("number", PhoneNo.getText().toString());
                                startActivity(forg);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Forgot_Pass_Activity.this, "Volley Error", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("phoneNo", PhoneNo.getText().toString());
                    return params;
                }

                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(request);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // check if user is registered in database
    private void CheckUser() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = hostinglink + "/Home/Checkuser";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);
                            if (result.getresult().equals("allreadyregistered")) {
                                //generate no.
                                generateRandomNumber();
                                //
                                Lyt_Phone.setVisibility(View.GONE);
                                Lyt_OTP.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(Forgot_Pass_Activity.this, "User not Registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Forgot_Pass_Activity.this, "Volley Error", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("phoneNo", PhoneNo.getText().toString());
                    return params;
                }

                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(request);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //To Generate a Random 4 Digit PIN
    public int generateRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s.append(number);
        }
        randomNumber = Integer.parseInt(s.toString());
        OTPPref();
        SendOTP_PIN();
        System.out.println(randomNumber);
        return randomNumber;
    }

    //To save OTP Pin in Shared Pref
    public void OTPPref() {
        mEditor.putInt("OTP_PIN", randomNumber);
        mEditor.commit();
    }

    //To Send OTP Msg to User
    public void SendOTP_PIN() {

        String msg = "OTP Code " + randomNumber;
        String ph = PhoneNo.getText().toString();

        String url = "http://sendpk.com" +
                "/api/sms.php?" +
                "username=" + "923054992224" +
                "&password=" + "ahmedishtiaq9777" +
                "&sender=" + "QuickMart" +
                "&mobile=" + ph + "&message=" + msg;
        //

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // enjoy your error status
                Toast.makeText(getApplicationContext(), "Error in Volley", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}