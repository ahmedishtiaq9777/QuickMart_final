package com.demotxt.myapp.recyclerview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.demotxt.myapp.recyclerview.R;
import com.demotxt.myapp.recyclerview.productlist.ShoppyProductListActivity;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
TextView signup,signin;
    EditText email,pass;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private  CheckBox checkBoxremember;
    private Boolean saveLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);




        signin= (TextView)findViewById(R.id.signin1);

        signup = (TextView)findViewById(R.id.signup);

        email=(EditText) findViewById(R.id.emaill);

        pass=(EditText) findViewById(R.id.passwordd);
        checkBoxremember=(CheckBox)findViewById(R.id.checkboxremember);



        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);


        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);

if(saveLogin==true) {

    email.setText(loginPreferences.getString("username", ""));
    pass.setText(loginPreferences.getString("password", ""));
    checkBoxremember.setChecked(true);

}



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

try {

    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    // String url = "http:// 192.168.10.13:64077/api/login";
    //String url="https://api.myjson.com/bins/kp9wz";
    String url = "http://ahmedishtiaqbutt-001-site1.atempurl.com/Home/login";


    StringRequest rRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response
                  //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    int size=response.toString().length();

String result=response.toString().substring(1,size-1);



                    if(result.equals("success")){

                        saveloginPrefference();

                        Intent intent=new Intent(Login.this, ShoppyProductListActivity.class);
                        intent.putExtra("email",email.getText().toString());
                        intent.putExtra("password",pass.getText().toString());
                        startActivity(intent);
                      // getconnection("http://ahmedishtiaqbutt-001-site1.atempurl.com/Home/getproducts/");





                   // finish();
                  //  Intent intent2=new Intent(Login.this,MainActivity2.class);

                  //  startActivity(intent2);
                    }else if(result.equals("sellersuccess")){

                        finish();
                         Intent intent2=new Intent(Login.this, MainActivity2.class);

                         startActivity(intent2);

                    }else {
                        Toast.makeText(getApplicationContext(),"Error:"+response.toString(),Toast.LENGTH_SHORT).show();
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


}catch (Exception E)
{
    Toast.makeText(getApplicationContext(),"Error: "+E.getMessage(),Toast.LENGTH_SHORT).show();
}




            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Login.this, Signup.class);
                startActivity(it);

            }
        });


    }


     public void saveloginPrefference()
    {
        if (checkBoxremember.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", email.getText().toString());
            loginPrefsEditor.putString("password", pass.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

    }
}
