package com.demotxt.myapp.recyclerview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.recyclerview.MainActivity;
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;

public class Signup extends AppCompatActivity {

    TextView signin;
    Button signup;
    EditText phone, password, userName;
    AwesomeValidation awesomeValidation;
    //for Auth
    int randomNumber;
    int range = 9;  // to generate a single number with this range, by default its 0..9
    int length = 4;
    int val;
    ConstraintLayout lyt_SignUP, lyt_Auth;
    EditText OTP_Txt;
    int userPin;
    Button AuthSubmit_Btn;
    Boolean isOTPSuccess = false;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    private SharedPreferences  loginpref;
    private StringResponceFromWeb result;
    private View layout;
    private SharedPreferences.Editor  loginprefeditor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        loginprefeditor = loginpref.edit();
        //INIT
        result = new StringResponceFromWeb();

        mPreferences = getApplicationContext().getSharedPreferences("OTPPREF",MODE_PRIVATE);
        mEditor = mPreferences.edit();

        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Signup.this, R.id.usernamee, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Signup.this, R.id.emaill, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
        awesomeValidation.addValidation(Signup.this, R.id.passwordd, RegexTemplate.NOT_EMPTY, R.string.error_password);

        //INITIALLIZE
        LayoutInflater inflater = getLayoutInflater();
        try {
            layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Loginactivity", "error" + e.getMessage());

        }


        signin = (TextView) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signin1);
        userName = (EditText) findViewById(R.id.usernamee);
        phone = (EditText) findViewById(R.id.emaill);
        password = (EditText) findViewById(R.id.passwordd);
        lyt_Auth = findViewById(R.id.lyt_Auth);
        lyt_SignUP = findViewById(R.id.lyt_SignUp);
        AuthSubmit_Btn = findViewById(R.id.Submit_Auth);
        OTP_Txt = findViewById(R.id.Txt_Pin);



        //Sign Up Button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumber();
                lyt_SignUP.setVisibility(View.GONE);
                lyt_Auth.setVisibility(View.VISIBLE);
            }
        });
        //

        //Auth Submit button
        AuthSubmit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting pin
                try {
                String pin = OTP_Txt.getText().toString();
                userPin = Integer.parseInt(pin);
                }catch (Exception e){
                    Toast.makeText(Signup.this, "UserPin Empty", Toast.LENGTH_SHORT).show(); }

                //Shared Pref for OTP PIN
                try {
                    val = mPreferences.getInt("OTP_PIN",0);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show(); }

                if (userPin == val){
                    isOTPSuccess = true;
                    AddUserSign();
                }
                else {
                    isOTPSuccess = false;
                    lyt_Auth.setVisibility(View.GONE);
                    lyt_SignUP.setVisibility(View.VISIBLE);
                }
            }
        });


    }
    //
    public void AddUserSign(){
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            String url = hostinglink + "/Home/signup";

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String struserid;

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);
                            if (result.getresult().equals("registered")) {
                                struserid = String.valueOf(result.getUserid());

                                int pid = 0;
                                Intent j = getIntent();
                                try {
                                    pid = j.getExtras().getInt("proid");
                                } catch (Exception e) {

                                }
                                if (pid != 0) {
                                    String strpid = String.valueOf(pid);
                                    AddToCart(struserid, strpid);
                                } else {
                                    Intent i = new Intent(Signup.this, MainActivity2.class);
                                    loginprefeditor.putBoolean("loggedin", true);
                                    loginprefeditor.putInt("userid", Integer.parseInt(struserid));
                                    loginprefeditor.commit();
                                    startActivity(i);
                                }

                            } else if (result.getresult().equals("alreadyregistered")) {
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



                    params.put("phoneNo",phone.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("userType", "C");
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

    //
    public void AddToCart(final String struserid, final String strpid )
    {
        try{

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";
            String url = hostinglink +"/Home/AddtoCart";

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result=gson.fromJson(response, StringResponceFromWeb.class);
                            if(result.getresult().equals("Added"))
                            {
                                finish();
                                Intent main =new Intent(Signup.this,MainActivity2.class);

                                main.putExtra("proid",Integer.parseInt(strpid));
                                startActivity(main);

                                // Toast.makeText(getApplicationContext(), "Product Added to Cart" , Toast.LENGTH_SHORT).show();
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

                    params.put("productId", strpid);
                    params.put("userId", struserid);

                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(rRequest);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //To Generate a Random 4 Digit PIN
    public int generateRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s = s + number;
        }
        randomNumber = Integer.parseInt(s);
        OTPPref();
        SendOTP_PIN();
        return randomNumber;
    }

    //To save OTP Pin in Shared Pref
    public void OTPPref() {;
        mEditor.putInt("OTP_PIN", randomNumber);
        mEditor.commit();
    }

    //To Send OTP Msg to User
    public void SendOTP_PIN() {

        String msg = "OTP Code " + randomNumber;
        String ph = phone.getText().toString();

        String url = "http://sendpk.com" +
                "/api/sms.php?" +
                "username=" + "923101767079" +
                "&password=" + "awais121" +
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



