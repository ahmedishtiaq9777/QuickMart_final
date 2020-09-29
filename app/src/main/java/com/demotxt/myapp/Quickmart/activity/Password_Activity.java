package com.demotxt.myapp.Quickmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.Quickmart.R;
import com.demotxt.myapp.Quickmart.ownmodels.SignInModel;
import com.demotxt.myapp.Quickmart.ownmodels.StringResponceFromWeb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.Quickmart.activity.MainActivity2.hostinglink;

public class Password_Activity extends AppCompatActivity {

    EditText Phone, OldPass, NewPass, ConfirmPass;
    Button ChangePass_Btn;
    StringResponceFromWeb result;
    String pass, con_Pass;
    String ph,pass1,pass2,pass3;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_);
        result = new StringResponceFromWeb();
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Password_Activity.this, R.id.number, RegexTemplate.NOT_EMPTY, R.string.error_contact);
        awesomeValidation.addValidation(Password_Activity.this, R.id.OldPass, RegexTemplate.NOT_EMPTY, R.string.error_password);
        awesomeValidation.addValidation(Password_Activity.this, R.id.newPass, RegexTemplate.NOT_EMPTY, R.string.error_password);
        awesomeValidation.addValidation(Password_Activity.this, R.id.newPassConfirm, RegexTemplate.NOT_EMPTY, R.string.error_password);



        Phone = findViewById(R.id.number);
        OldPass = findViewById(R.id.OldPass);
        NewPass = findViewById(R.id.newPass);
        ConfirmPass = findViewById(R.id.newPassConfirm);
        ChangePass_Btn = findViewById(R.id.ChangePass_Btn);

        //
        Intent i = getIntent();
        String no = Objects.requireNonNull(i.getExtras()).getString("number");
        String p = i.getExtras().getString("password");

        //
        Phone.setText(no);
        OldPass.setText(p);
        Phone.setFocusable(false);
        OldPass.setFocusable(false);
        //

        pass = NewPass.getText().toString();
        con_Pass = ConfirmPass.getText().toString();


        ChangePass_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    ph = Phone.getText().toString();
                    pass1 = OldPass.getText().toString();
                    pass2 = NewPass.getText().toString();
                    pass3 = ConfirmPass.getText().toString();
                    SignInModel m = new SignInModel(ph, OldPass, NewPass, ConfirmPass);


                    if (pass.equals(con_Pass)) {
                        ForgetPassword();

                    } else {
                        Toast.makeText(Password_Activity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void ForgetPassword() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = hostinglink + "/Home/UpdatePassword";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            result = gson.fromJson(response, StringResponceFromWeb.class);

                            if (result.getresult().equals("success")) {
                                Toast.makeText(Password_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else if (result.getresult().equals("null")) {
                                Toast.makeText(Password_Activity.this, "null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Password_Activity.this, "Volley Error", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("phoneNo", Phone.getText().toString());
                    params.put("oldpassword", OldPass.getText().toString());
                    params.put("newpassword", NewPass.getText().toString());
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
}