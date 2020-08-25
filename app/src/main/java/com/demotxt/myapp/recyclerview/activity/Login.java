package com.demotxt.myapp.recyclerview.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.demotxt.myapp.recyclerview.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;


public class Login extends AppCompatActivity {
    TextView signup;
    Button signin;
    private EditText email, pass;
    AwesomeValidation awesomeValidation;

    private SharedPreferences rememberMepref, loginpref;
    private SharedPreferences.Editor rememberMePrefsEditor, loginprefeditor;
    private CheckBox checkBoxremember;
    private Boolean saveLogin;
    private SharedPreferences cartlistpref;
    private SharedPreferences.Editor cartlistprefeditor;
    private int proid;
    public Set<String> cartids;
    StringResponceFromWeb result;
    View layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);
        result=new StringResponceFromWeb();

        LayoutInflater inflater = getLayoutInflater();
        try{
            layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_root));//for product added :to make custom toast with tick mark

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"ERROR:"+e.getMessage(),Toast.LENGTH_SHORT).show();
            Log.i("Loginactivity","error"+e.getMessage());

        }


        cartids = new HashSet<String>();

        signin = (Button) findViewById(R.id.signin1);

        signup = (TextView) findViewById(R.id.signup);

        email = (EditText) findViewById(R.id.emaill);

        pass = (EditText) findViewById(R.id.passwordd);
        checkBoxremember = (CheckBox) findViewById(R.id.checkboxremember);

        cartlistpref = getSharedPreferences("cartprefs", MODE_PRIVATE);//get cartpreferences that contains cartitemlist

        loginpref = getSharedPreferences("loginpref", MODE_PRIVATE);
        rememberMepref = getSharedPreferences("remembermepref", MODE_PRIVATE);

        cartlistprefeditor = cartlistpref.edit();
        rememberMePrefsEditor = rememberMepref.edit();
        loginprefeditor = loginpref.edit();

        cartids = cartlistpref.getStringSet("cartids", cartids);
        saveLogin = rememberMepref.getBoolean("saveLogin", false);

        if (saveLogin == true) {

            email.setText(rememberMepref.getString("username", ""));
            pass.setText(rememberMepref.getString("password", ""));
            checkBoxremember.setChecked(true);

        }





        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                awesomeValidation = new AwesomeValidation(BASIC);
                awesomeValidation.addValidation(Login.this, R.id.emaill, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
                awesomeValidation.addValidation(Login.this, R.id.passwordd, RegexTemplate.NOT_EMPTY, R.string.error_password);

                try {

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    // String url = "http:// 192.168.10.13:64077/api/login";
                    //String url="https://api.myjson.com/bins/kp9wz";
                    String url = hostinglink +"/Home/login";


                    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {


                                        JSONObject user = new JSONObject(response);
                                        String strResult = user.getString("result");
                                        int userid = user.getInt("userid");

                                        // response
                                        //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                        //  int size=response.toString().length();

//String result=response.toString().substring(1,size-1);


                                        if (strResult.equals("success")) {

                                            saveloginPrefference(userid);
                                            Intent i = getIntent();
                                            int pid = -1;
                                            boolean login_from_profile=false;
                                            try{
                                                pid = i.getExtras().getInt("proid");
                                            }catch (Exception e)
                                            {
                                                Log.i("Came from Profile","login through profile fragment");
                                                Log.i("Exception",e.getMessage());
                                            }
                                            try{
                                                login_from_profile=i.getExtras().getBoolean("loginfromprofile");
                                            }catch (Exception e)
                                            {
                                                Log.i("ProdActivity","login through ProdActivity");
                                                Log.i("Exception",e.getMessage());
                                            }

                                            if (pid != -1) {
                                                String struserid=String.valueOf(loginpref.getInt("userid",0));
                                                String  strpid=String.valueOf(pid);
                                                AddToCart(struserid,strpid);
                                                // finish();
                                                Intent mainactivity2 = new Intent(Login.this, MainActivity2.class);
                                              //  mainactivity2.putExtra("code", 5);
                                                startActivity(mainactivity2);
                                            }else if(login_from_profile)
                                            { Intent mainactivity2 = new Intent(Login.this, MainActivity2.class);
                                                startActivity(mainactivity2);
                                            }

                                            //   Intent intent = new Intent(Login.this, ShoppyProductListActivity.class);        //this is for searching ....product list
                                            //  intent.putExtra("email", email.getText().toString());
                                            //    intent.putExtra("password", pass.getText().toString());
                                            //    startActivity(intent);
                                            // getconnection("http://ahmedishtiaqbutt-001-site1.atempurl.com/Home/getproducts/");


                                            // finish();
                                            //  Intent intent2=new Intent(Login.this,MainActivity2.class);

                                            //  startActivity(intent2);
                                        } else if (strResult.equals("sellersuccess")) {

                                            finish();
                                            Intent intent2 = new Intent(Login.this, MainActivity2.class);

                                            startActivity(intent2);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error:" + response.toString(), Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(getApplicationContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            params.put("password", pass.getText().toString());
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
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Login.this, Signup.class);
                try {
                    Intent i = getIntent();
                    int pid = i.getExtras().getInt("proid");
                    it.putExtra("proid", pid);
                }catch (Exception e)
                {

                    Log.i("Signupfromprofile","intent in login for proid:"+e.getMessage());
                }
                startActivity(it);

            }
        });

    }




    public void AddToCart(final String struserid, final String strpid)
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


                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();


                                // Toast.makeText(getApplicationContext(), "Product Added to Cart" , Toast.LENGTH_SHORT).show();


                            } else if (result.getresult().equals("AllreadyAdded")) {
                                // Toast.makeText(getApplicationContext(), "Product is Already Added", Toast.LENGTH_LONG).show();
                                // response
                                try {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Login.this);
                                    builder1.setTitle("Already Added");
                                    builder1.setMessage("Your product is Already Added to Cart!");

                                    builder1 .setIcon(R.drawable.exclamationmarkresize);
                                    // builder1.show();
                                    AlertDialog alert11 = builder1.create();

                                    alert11.show();
                                    Thread.sleep(3000);
                               //   Intent main=new Intent(Login.this,MainActivity2.class);
                                 // startActivity(main);

                                } catch (Exception e) {
                                    Log.i("error:", e.getMessage());
                                    Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }




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


/*
    public void AddToCart(int id) {
        String strid = String.valueOf(id);
        cartids.add(strid);
        cartlistprefeditor.remove("cartids");
        cartlistprefeditor.commit();
        cartlistprefeditor.putStringSet("cartids", cartids);
        cartlistprefeditor.commit();
    }*/

    public void saveloginPrefference(int uid) {

        loginprefeditor.putBoolean("loggedin", true);
        loginprefeditor.putInt("userid", uid);
        loginprefeditor.commit();
        if (checkBoxremember.isChecked()) {
            rememberMePrefsEditor.putString("username", email.getText().toString());
            rememberMePrefsEditor.putString("password", pass.getText().toString());
            rememberMePrefsEditor.commit();
        } else {
            rememberMePrefsEditor.clear();
            rememberMePrefsEditor.commit();
        }
    }

}
