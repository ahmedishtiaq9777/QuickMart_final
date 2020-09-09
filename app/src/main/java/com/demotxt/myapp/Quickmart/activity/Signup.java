package com.demotxt.myapp.Quickmart.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.SignUpModel;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.demotxt.myapp.Quickmart.ownmodels.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Signup extends AppCompatActivity {

    TextView signin;
    Button signup;
    EditText phone, password, userName;
    AwesomeValidation awesomeValidation;
    //for Auth
    int randomNumber, val, userPin;
    int range = 9;  // to generate a single number with this range, by default its 0..9
    int length = 4;
    ConstraintLayout lyt_SignUP, lyt_Auth;
    EditText OTP_Txt;
    Button AuthSubmit_Btn;
    CheckBox checkBox;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    private SharedPreferences loginpref;
    private StringResponceFromWeb result;
    private View layout;
    private SharedPreferences.Editor loginprefeditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);
        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        loginprefeditor = loginpref.edit();
        //INIT
        result = new StringResponceFromWeb();

        mPreferences = getApplicationContext().getSharedPreferences("OTPPREF", MODE_PRIVATE);
        mEditor = mPreferences.edit();

        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Signup.this, R.id.USERNAME, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Signup.this, R.id.PHONE, "^0(?=3)[0-9]{10}$", R.string.error_contact);
        awesomeValidation.addValidation(Signup.this, R.id.PASSWORD, RegexTemplate.NOT_EMPTY, R.string.error_password);

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
        userName = (EditText) findViewById(R.id.USERNAME);
        phone = (EditText) findViewById(R.id.PHONE);
        password = (EditText) findViewById(R.id.PASSWORD);
        checkBox = (CheckBox) findViewById(R.id.checkboxAgree);
        lyt_Auth = findViewById(R.id.lyt_Auth);
        lyt_SignUP = findViewById(R.id.lyt_SignUp);
        AuthSubmit_Btn = findViewById(R.id.Submit_Auth);
        OTP_Txt = findViewById(R.id.Txt_Pin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });


        //Sign Up Button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    String name = userName.getText().toString();
                    String pass = password.getText().toString();
                    String contact = phone.getText().toString();
                    SignUpModel m = new SignUpModel(name, contact, pass);
                    UserModel model = new UserModel();
                    model.setPhone(contact);
                    model.setUserName(name);
                    CheckUser();
                    Checkcheckbox();
                }
            }
        });
        //

        loginprefeditor.putString("Name", userName.getText().toString());

        //Auth Submit button
        AuthSubmit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting pin
                try {
                    String pin = OTP_Txt.getText().toString();
                    userPin = Integer.parseInt(pin);
                } catch (Exception e) {
                    Toast.makeText(Signup.this, "UserPin Empty", Toast.LENGTH_SHORT).show();
                }

                //Shared Pref for OTP PIN
                try {
                    val = mPreferences.getInt("OTP_PIN", 0);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (userPin == val) {
                    AddUserSign();
                } else {
                    lyt_Auth.setVisibility(View.GONE);
                    lyt_SignUP.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void Checkcheckbox() {
        if (checkBox.isChecked()) {
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Signup.this);
            builder1.setTitle("Please Agree");
            builder1.setMessage("Please agree to our terms and conditions!");

            builder1.setIcon(R.drawable.exclamationmarkresize);
            // builder1.show();
            AlertDialog alert11 = builder1.create();

            alert11.show();

        }
    }

    //
    public void CheckUser() {
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
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Signup.this);
                                builder1.setTitle("Already Registered");
                                builder1.setMessage("Try with another phone number!");

                                builder1.setIcon(R.drawable.exclamationmarkresize);
                                // builder1.show();
                                AlertDialog alert11 = builder1.create();

                                alert11.show();

                            } else if (result.getresult().equals("NewUser")) {
                                Toast.makeText(Signup.this, "New User", Toast.LENGTH_SHORT).show();
                                //
                                generateRandomNumber();
                                lyt_SignUP.setVisibility(View.GONE);
                                lyt_Auth.setVisibility(View.VISIBLE);
                            } else {


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Signup.this, "Volley Error", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phoneNo", phone.getText().toString());
                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(request);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //
    public void AddUserSign() {
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

                    params.put("phoneNo", phone.getText().toString());
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
    public void AddToCart(final String struserid, final String strpid) {
        try {

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            // String url = "http:// 192.168.10.13:64077/api/login";
            //String url="https://api.myjson.com/bins/kp9wz";
            String url = hostinglink + "/Home/AddtoCart";

            StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);
                            if (result.getresult().equals("Added")) {
                                finish();
                                Intent main = new Intent(Signup.this, MainActivity2.class);

                                main.putExtra("proid", Integer.parseInt(strpid));
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


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void OTPPref() {
        ;
        mEditor.putInt("OTP_PIN", randomNumber);
        mEditor.commit();
    }

    //To Send OTP Msg to User
    public void SendOTP_PIN() {

        String msg = "OTP Code " + randomNumber;
        String ph = phone.getText().toString();

        String url = "http://sendpk.com" +
                "/api/sms.php?" +
                "username=" + "923046279543" +
                "&password=" + "1234" +
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



