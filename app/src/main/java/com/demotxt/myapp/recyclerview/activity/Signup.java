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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.demotxt.myapp.recyclerview.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.demotxt.myapp.recyclerview.activity.MainActivity2.hostinglink;

public class Signup extends AppCompatActivity {

    TextView signin, signup;
    EditText email, password, userName;
    AwesomeValidation awesomeValidation;
    Spinner spiner;
    public String selectedaccount;
    //facebook
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignup);
        //INIT

        awesomeValidation = new AwesomeValidation(BASIC);

        awesomeValidation.addValidation(Signup.this, R.id.usernamee, "[a-zA-Z\\s]+", R.string.error_name);
        awesomeValidation.addValidation(Signup.this, R.id.emaill, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
        awesomeValidation.addValidation(Signup.this, R.id.passwordd, RegexTemplate.NOT_EMPTY, R.string.error_password);

//INITIALLIZE
        signin = (TextView) findViewById(R.id.signin);
        signup = (TextView) findViewById(R.id.signin1);
        userName= (EditText) findViewById(R.id.usernamee);
        email = (EditText) findViewById(R.id.emaill);
        password = (EditText) findViewById(R.id.passwordd);
        //facebook
        mLoginButton = findViewById(R.id.login_button);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setPermissions(Arrays.asList("email", "public_profile"));

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    // String url = "http:// 192.168.10.13:64077/api/login";
                    //String url="https://api.myjson.com/bins/kp9wz";
                    String url = hostinglink +"/Home/signup";

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker mTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            loadUserProfile(currentAccessToken);
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                    //

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }
}

